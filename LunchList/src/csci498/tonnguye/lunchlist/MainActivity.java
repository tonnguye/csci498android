package csci498.tonnguye.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	List<Restaurant> model = new ArrayList<Restaurant>();
	List<String> addresses = new ArrayList<String>();
	RestaurantAdapter adapter = null;
	ArrayAdapter<String> addressAdapter = null;
	RadioGroup types = null;
	RadioButton button;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button save = (Button)findViewById(R.id.save);

		save.setOnClickListener(onSave);
		addAList();
		
		types = (RadioGroup)findViewById(R.id.types);
		button = new RadioButton(this);
		button.setText("Amazing Button");
		types.addView(button);
		
		
	}
	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			
			Restaurant r = new Restaurant();
			EditText name = (EditText)findViewById(R.id.name);
			AutoCompleteTextView address = (AutoCompleteTextView)findViewById(R.id.addr);
		
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			addAlotOfRadioButtons(r);
			
			address.setAdapter(addressAdapter);
			adapter.add(r);
			addressAdapter.add(address.getText().toString());
			
		}
	};
	
	
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
	
	private void addAList(){
		
		Spinner list=(Spinner)findViewById(R.id.restaurants);
	    adapter=new RestaurantAdapter();
	    list.setAdapter(adapter);
	    
	    addressAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, addresses);
	}
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		
		RestaurantAdapter() {
			
			super(MainActivity.this,
					android.R.layout.simple_list_item_1,
					model);
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