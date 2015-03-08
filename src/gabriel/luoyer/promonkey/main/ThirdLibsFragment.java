package gabriel.luoyer.promonkey.main;


import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.base.BaseFragment;
import gabriel.luoyer.promonkey.dialog.TianKongDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThirdLibsFragment extends BaseFragment  {
	
	private TextView text_studentID,text_studentName,text_studentPassword;
	private RelativeLayout rl_studentPassword;
	private View view;
	private SharedPreferences sharedata;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.activity_leavehome, container, false);
		initViews();
		initEvents();
		fullData();
		return view;
	}

	protected void initViews() {
		// TODO Auto-generated method stub
		rl_studentPassword = (RelativeLayout) view.findViewById(R.id.rl_studentPassword);
		text_studentID = (TextView) view.findViewById(R.id.text_studentID);
		text_studentName = (TextView) view.findViewById(R.id.text_studentName);
		text_studentPassword = (TextView) view.findViewById(R.id.text_studentPassword);
		sharedata = getActivity().getSharedPreferences("user", 0); 
	}

	protected void initEvents() {
		rl_studentPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	String tag = "my_dialog";  
            	TianKongDialog myFragment = TianKongDialog.newInstance(sharedata.getString("U_studentID", null),getActivity());  
                 myFragment.show(getFragmentManager(), tag);  
                 
            }
        });
	}
	
	protected void fullData() {
		 text_studentID.setText(sharedata.getString("U_studentID", null));
		 text_studentName.setText(sharedata.getString("U_Name", null));
	}

	
}
