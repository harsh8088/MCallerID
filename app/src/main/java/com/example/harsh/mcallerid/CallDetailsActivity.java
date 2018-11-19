package com.example.harsh.mcallerid;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.harsh.mcallerid.utils.CallLogHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CallDetailsActivity extends AppCompatActivity {


    private ArrayList<CallerModel> callerList;

    private CallDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_details);

        RecyclerView recyclerView = findViewById(R.id.rv_caller_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new CallDetailsAdapter(this);
        recyclerView.setAdapter(adapter);
        Cursor curLog = CallLogHelper.getAllCallLogs(getContentResolver());
        setCallLogs(curLog);


    }


    private void setCallLogs(Cursor curLog) {
        callerList = new ArrayList<>();
        while (curLog.moveToNext()) {
            CallerModel callerModel = new CallerModel();
            String callNumber = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.NUMBER));
            callerModel.setConNumbers(callNumber);

            String callName = curLog
                    .getString(curLog
                            .getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
            if (callName == null) {
                callerModel.setConNames("Unknown");
            } else
                callerModel.setConNames(callName);

            String callDate = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DATE));
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MMM-yyyy HH:mm", Locale.getDefault());
            String dateString = formatter.format(new Date(Long
                    .parseLong(callDate)));
            callerModel.setConDate(dateString);

            String callType = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.TYPE));
            if (callType.equals("1")) {
                callerModel.setConType("Incoming");
            } else
                callerModel.setConType("Outgoing");

            String duration = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DURATION));
            callerModel.setConTime(duration);
            callerList.add(callerModel);
        }

        adapter.setList(callerList);
        curLog.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater imf = getMenuInflater();
        imf.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}