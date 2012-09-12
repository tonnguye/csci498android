package csci498.tonnguye.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class LunchList extends TabActivity {
	Restaurant r = new Restaurant();
	List<Restaurant> model = new ArrayList<Restaurant>();
	List<String> addresses = new ArrayList<String>();

	RestaurantAdapter adapter = null;
	ArrayAdapter<String> addressAdapter = null;

	Restaurant current = null;
	RadioGroup types   = null;
	EditText name      = null;
	EditText address   = null;
	EditText note      = null;
	int progress;


	private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Restaurant r = model.get(position);
			name.setText(r.getName());
			address.setText(r.getAddress());
			note.setText(r.getNotes());

			if (r.getType().equals("sit_down")) {
				types.check(R.id.sit_down);
			}

			else if (r.getType().equals("take_out")) {
				types.check(R.id.take_out);
			}

			else {
				types.check(R.id.delivery);
			}

			getTabHost().setCurrentTab(1);

		}
	};

	private void doSomeLongWork(final int incr) {
		SystemClock.sleep(250); // should be something more useful!
	}

	private Runnable longTask=new Runnable() {
		public void run() {
			for (int i=0;i<20;i++) {
				doSomeLongWork(500);
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.main);
		Button save = (Button)findViewById(R.id.save);

		save.setOnClickListener(onSave);
		addInformation();
		addAList();
		addTabs();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.option, menu);
		return(super.onCreateOptionsMenu(menu));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==R.id.toast) {
			String message="No restaurant selected";
			if (current!=null) {
				message=current.getNotes();
			}
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			return(true);
		}
		else if (item.getItemId() == R.id.run) {
			new Thread(longTask).start();
		}
		return(super.onOptionsItemSelected(item));
	}


	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			current = new Restaurant();

			setInformation();
			addAlotOfRadioButtons(current);

			AutoCompleteTextView address = (AutoCompleteTextView)findViewById(R.id.addr);
			address.setAdapter(addressAdapter);
			adapter.add(current);
			addressAdapter.add(address.getText().toString());

		}
	};

	private void addTabs(){
		TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
		spec.setContent(R.id.restaurants);
		spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
		getTabHost().addTab(spec);

		spec=getTabHost().newTabSpec("tag2");
		spec.setContent(R.id.details);
		spec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
		getTabHost().addTab(spec);
		getTabHost().setCurrentTab(1);
	}


	private void addAlotOfRadioButtons(Restaurant r){

		switch (types.getCheckedRadioButtonId()) {
		case R.id.sit_down:
			r.setType("sit_down");
			break;

		case R.id.take_out:
			r.setType("take_out");
			break;

		case R.id.delivery:
			r.setType("delivery");
			break;
		}
	}

	private void addInformation() {
		name    = (EditText)findViewById(R.id.name);
		address = (EditText)findViewById(R.id.addr);
		note    = (EditText)findViewById(R.id.notes);
		types   = (RadioGroup)findViewById(R.id.types);

	}

	private void setInformation() {
		EditText name = (EditText)findViewById(R.id.name);


		current.setName(name.getText().toString());
		current.setAddress(address.getText().toString());
		current.setNotes(note.getText().toString());

	}


	private void addAList(){

		ListView list=(ListView)findViewById(R.id.restaurants);
		adapter=new RestaurantAdapter();
		list.setAdapter(adapter);
		list.setOnItemClickListener(onListClick);

		addressAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, addresses);
	}

	class RestaurantAdapter extends ArrayAdapter<Restaurant> {

		RestaurantAdapter() {

			super(LunchList.this, android.R.layout.simple_list_item_1, model);
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View row = convertView;
			RestaurantHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row=inflater.inflate(R.layout.row, parent, false);
				holder = new RestaurantHolder(row);
				row.setTag(holder);
			}

			else {
				holder = (RestaurantHolder)row.getTag();
			}

			holder.populateFrom(model.get(position));
			return(row);
		}


	}

	static class RestaurantHolder {

		private TextView name    = null;
		private TextView address = null;
		private ImageView icon   = null;

		RestaurantHolder(View row) {
			name    = (TextView)row.findViewById(R.id.title);
			address = (TextView)row.findViewById(R.id.address);
			icon    = (ImageView)row.findViewById(R.id.icon);
		}

		void populateFrom(Restaurant r) {
			name.setText(r.getName());
			address.setText(r.getAddress());

			if(r.getName().equals("Sushi")) {
				name.setTextColor(Color.BLUE);
			}

			if (r.getType().equals("sit_down")) {
				icon.setImageResource(R.drawable.ball_red);
			}

			else if (r.getType().equals("take_out")) {
				icon.setImageResource(R.drawable.ball_yellow);
			}

			else {
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}

}