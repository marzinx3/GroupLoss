package marzin.com.thegrouploss.utils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
/**
 * Created by coolm_000 on 11/12/2017.
 */

public class SearchSpinnerListener implements OnItemSelectedListener{
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        Toast.makeText(adapterView.getContext(),
                "OnItemSelectedListener : " + adapterView.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
