package d3vel0pper.com.tictactoetest;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class ChoiceFragment extends DialogFragment implements View.OnClickListener {

    private MainActivity parent;

    public ChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        parent = (MainActivity)getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstantState){
        super.onActivityCreated(savedInstantState);
        //set Attributes
        Dialog dialog = getDialog();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float dialogWidth = 300 * metrics.scaledDensity;
        layoutParams.width = (int)dialogWidth;
        dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choice, container, false);
        Button forwardBtn = (Button)view.findViewById(R.id.forward_btn);
        Button afterBtn = (Button)view.findViewById(R.id.after_btn);
        forwardBtn.setOnClickListener(this);
        afterBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstantState){
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        return dialog;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.forward_btn:
                parent.setAiTurn(-1);
                Toast.makeText(parent,"ゲームスタート！！\nあなたが先攻です",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.after_btn:
                parent.setAiTurn(1);
                if(!parent.ai.runAi()){
                    Toast.makeText(parent,"AIは予期せぬ動作で終了しました",Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }
                if(parent.checkIsGameOver()){
                    dismiss();
                    return;
                }
                Toast.makeText(parent,"ゲームスタート！！\nあなたは後攻です",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog){
        parent.setAiTurn(-1);
        Toast.makeText(parent,"ゲームスタート！！\nあなたが先攻です",Toast.LENGTH_SHORT).show();
    }

}
