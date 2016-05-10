package sagar.tooltip;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Sagar on 10-05-2016.
 */
public class ToolTip {
    View element;
    String content;
    Context context;

    private static int semaphore=0;
    private int timer = 9999*1000;
    private LayoutInflater layoutInflater;
    private View popupView;
    private PopupWindow popupWindow;
    private TextView toolTipText;
    private ImageView tooltipDownPointer;
    private ImageView tooltipUpPointer;
    private LinearLayout toolTipMainFrame;
    private View bottomFrame;
    private View topFrame;
    private ViewGroup contentHolder;
    private int position=0;
    private Drawable preBckgrd;
    private GradientDrawable drawable;

    public ToolTip(Context context, View elementId, String content) {
        this.element = elementId;
        this.content = content;
        this.context = context;

        semaphore++;

//Start Setting ToolTip Components
        layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.tooltip, null);
        popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        toolTipText = (TextView) popupView.findViewById(R.id.tooltip_text);
        tooltipDownPointer = (ImageView) popupView.findViewById(R.id.tooltip_pointer_up);
        tooltipUpPointer = (ImageView) popupView.findViewById(R.id.tooltip_pointer_down);
        toolTipMainFrame = (LinearLayout) popupView.findViewById(R.id.tooltip_frame);
        bottomFrame = popupView.findViewById(R.id.tooltip_bottomframe);
        topFrame = popupView.findViewById(R.id.tooltip_topframe);
        contentHolder = (ViewGroup) popupView.findViewById(R.id.tooltip_contentholder);
//Stop Setting ToolTip Components

    }

    public void setColor(int color){

        tooltipUpPointer.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        tooltipDownPointer.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        topFrame.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        bottomFrame.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        contentHolder.setBackgroundColor(color);
    }

    public void setTextColor(int color){

        toolTipText.setTextColor(color);
    }

    public void setTextPositon(int position){

        this.position = position;
    }

    public void showToolTipBelow(){

        toolTipText.setText(content);
        tooltipDownPointer.setVisibility(View.VISIBLE);
        tooltipUpPointer.setVisibility(View.GONE);
        toolTipMainFrame.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }

        });

        popupWindow.showAsDropDown(element, position, 0, Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

            }
        }, timer);

        preBckgrd = element.getBackground();
        element.setBackground(drawable);

    }

    public void showToolTipAbove(){

        toolTipText.setText(content);
        tooltipUpPointer.setVisibility(View.VISIBLE);
        tooltipDownPointer.setVisibility(View.GONE);
        toolTipMainFrame.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }

        });
        popupWindow.showAsDropDown(element, -20, -180);

    }

    public void closeToolTipSynchronously(){
        if(semaphore==1) {
            element.setBackground(preBckgrd);
            popupWindow.dismiss();
        }
        semaphore--;
    }

    public void closeToolTip(){
        element.setBackground(preBckgrd);
        popupWindow.dismiss();
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

}
