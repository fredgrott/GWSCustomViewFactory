package gws.grottworkshop.gwscustomviewfactoryapl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * GWSCustomViewFactory, we have to handle tow cases. The first one is
 * kind of easy as in a regualr activity and you often see code such as :
 * <code>
 * public class GWSCustomViewFactory implements LayoutInflater.Factory {
 *  private static GWSCustomViewFactory mInstance;
 *
 *   public static GWSCustomViewFactory getInstance () {
 *      if (mInstance == null) {
 *          mInstance = new GWSCustomViewFactory();
 *      }
 *
 *     return mInstance;
 *  }
 *
 *  private GWSCustomViewFactory () {}
 *
 *  public View onCreateView (String name, Context context, AttributeSet attrs) {
 *       //Check if it's one of our custom classes, if so, return one using
 *      //the Context/AttributeSet constructor
 *      if (MyCustomView.class.getSimpleName().equals(name)) {
 *           return new MyCustomView(context, attrs);
 *      }
 *
 *      //Not one of ours; let the system handle it
 *     return null;
 *  }
 * }
 *
 * </code>
 *
 * Very simplistic, with the custom views hard wired. No not this way, we can instead
 * do a method that is short CreateViewByInflator that checks for our custom view prefix and
 * will add the full package name and create the view. Yes, it short cuts the full
 * logic of the LayoutInflator, hence the catch clause block. However, dumbing it down this way is
 * somewhat faster as we do not do any class searching, etc.
 *
 * And by happy fate we also solve the other case in that if we are defining fragments in xml they
 * do not goof up due to the way android compatibility library(android support library)handles fragment
 * lifecycle..
 *
 * Usage:
 *
 * Yes, set the custom view prefix and full package name first be you use the factory and yes
 * package them in one package instead of spreading them around.
 *  Than in your onCreate:
 *  <code>
 *  LayoutInflater.from(this).setFactory(GWSCustomViewsFactory.onCreateView(String name, Context context, AttributeSet attrs));
 *  </code>
 *
 *
 *
 * @author Fred Grott
 *
 */
public  class GWSCustomViewFactory implements LayoutInflater.Factory {

	private LayoutInflater mInflater;

	public String cvPrefix = "cv.";

	public String fpnCustomView = "our.full.package.name.";

	public String getFPNCustomView() {
		return fpnCustomView;
	}

	public void setFPNCustomView(String ourFPNCustomView) {
		fpnCustomView = ourFPNCustomView;
	}

	public String getCVPrefix(){
		return cvPrefix;
	}

	public void setCSPrefix(String ourCVPrefix){
		cvPrefix = ourCVPrefix;
	}

	public GWSCustomViewFactory(LayoutInflater inflater){
        this.mInflater = inflater;

	}

	protected View CreateViewByInflater(String name, Context context,
            AttributeSet attrs) {
    try {
            String viewFullName = "android.widget." + name;
            if ((name=="View") || (name=="ViewGroup"))
                    viewFullName = "android.view." + name;
            else if (name.startsWith(this.getCVPrefix())){
                    viewFullName = this.getFPNCustomView() + name.substring(name.indexOf('.') + 1);
            }else if (name.contains("."))
                    viewFullName = name;

            View view = mInflater.createView(viewFullName, null,
                            attrs);
            return view;
      } catch (Exception e) {
            e.printStackTrace();
            return null;
      }
    }

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View view = this.CreateViewByInflater(name, context, attrs);
        if (view==null) return null;
        return view;
	}


}
