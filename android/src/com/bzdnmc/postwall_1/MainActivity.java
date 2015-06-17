package com.bzdnmc.postwall_1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.photowallfallsdemo.Images;


public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	    static View rootView;
	    static String tag = "0";
	    
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	public static NavigationDrawerFragment mNavigationDrawerFragment;
	public static int count;
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	public static Handler myHandler = new Handler() {  
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 11:
                	   MainActivity.mNavigationDrawerFragment.selectItem(0);
                       break;   
             }   
             super.handleMessage(msg);   
        }   
   };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	    count = 0;
	    try {
			netThread.getList("0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		private static int id;
		
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment(sectionNumber);
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
		}

		public PlaceholderFragment(int sectionNumber) {
			id = sectionNumber;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (id == 1) {
				rootView = inflater.inflate(R.layout.main_view, container,
						false);
				return rootView;
			}
			if (id == 2) {
				rootView = inflater.inflate(R.layout.fragment_view_1, container,
						false);
				
				ImageButton button1 =  (ImageButton) rootView.findViewById(R.id.button1);
				ImageButton button2 =  (ImageButton) rootView.findViewById(R.id.button2);
				ImageButton button3 =  (ImageButton) rootView.findViewById(R.id.button3);
				ImageButton button4 =  (ImageButton) rootView.findViewById(R.id.button4);
				ImageButton button5 =  (ImageButton) rootView.findViewById(R.id.button5);
				ImageButton button6 =  (ImageButton) rootView.findViewById(R.id.button6);
				
				setListener(button1, "0");
				setListener(button2, "1");
				setListener(button3, "2");
				setListener(button4, "3");
				setListener(button5, "4");
				setListener(button6, "5");
				return rootView;
			}else {
				rootView = inflater.inflate(R.layout.fragment_view_2, container,
						false);
				return rootView;
			}
		}

		private void setListener(ImageButton button,final String _tag) {
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Images.imageUrls.clear();
					count = 0;
					tag = _tag;
					mNavigationDrawerFragment.selectItem(0);
					netThread.getList(tag);
				}
			});
		}
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}
