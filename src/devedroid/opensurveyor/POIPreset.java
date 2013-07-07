package devedroid.opensurveyor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import devedroid.opensurveyor.data.Marker;
import devedroid.opensurveyor.data.POI;
import devedroid.opensurveyor.data.SessionManager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class POIPreset extends BasePreset {

	public final String type;
	public final String icon;
	boolean toggle = false;
	boolean directed = false;

	private final List<String> propsNames;

	public POIPreset(String title, String type, String icon, boolean toggle) {
		super(title);
		if (type == null)
			this.type = title.toLowerCase();
		else
			this.type = type;
		this.icon = icon;
		this.propsNames = new ArrayList<String>();
		this.toggle = toggle;
	}

	public POIPreset(String title, String type, String icon) {
		this(title, type, icon, false);
	}

	public POIPreset(String title, String type) {
		this(title, type, null, false);
	}

	public POIPreset(String title) {
		this(title, null, null, false);
	}

	public void addProperty(String p) {
		propsNames.add(p);
	}

	static float buttonTextSize = Float.NaN;

	@Override
	public boolean isToggleButton() {
		return toggle;
	}
	
	@Override
	public boolean isDirected() {
		return directed;
	}

	@Override
	public Button createButton(Context context, final SessionManager sm) {
		final Button res;
		if (isToggleButton()) {
			final ToggleButton tres = new ToggleButton(context);
			tres.setTextOn(title);
			tres.setTextOff(title);
			if (Float.isNaN(buttonTextSize))
				buttonTextSize = new Button(context).getTextSize();
			tres.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize);
			res = tres;
		} else {
			res = new Button(context);
		}
		res.setTag(this);
		res.setText(title);
		res.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				POI mm = new POI(POIPreset.this);
				if (isToggleButton())
					mm.addProperty("linear",
						((ToggleButton) res).isChecked() ? "start" : "end");
				sm.addMarker(mm);

			}
		});
		// if(icon!=null) {
		// int rid = context.getResources().getIdentifier("marker_"+icon,
		// "drawable",
		// context.getApplicationInfo().packageName);
		// if(rid != 0) {
		// Drawable dr = context.getResources().getDrawable(rid);
		// //Utils.logd("MarkerButton", dr.toString());
		// //setCompoundDrawables(null, dr, null, null);
		// //res.setGravity(Gravity.CENTER);
		// //res.setPadding(res.getPaddingLeft(), res.getPaddingTop()+30,
		// res.getPaddingRight(), res.getPaddingBottom() );
		// //res.setCompoundDrawablePadding(-10);
		// res.setCompoundDrawablesWithIntrinsicBounds(null, dr, null, null);
		// }
		// }

		return res;
	}

	@Override
	public List<String> getPropertyNames() {
		return propsNames;
	}

}
