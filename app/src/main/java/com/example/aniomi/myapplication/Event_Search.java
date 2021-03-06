package com.example.aniomi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

/**
 * Created by asif on 11/3/17.
 */

public class Event_Search{

    private FirebaseAuth mAuth;
    SpinnerDialog attrdialog;
    private Toolbar toolbar;
    private DatabaseReference mDatabase,status;

    List<EventDetails> listEvent = new ArrayList<EventDetails>();
    ArrayList<String> listName=new ArrayList<>();
    ArrayList<String> attending=new ArrayList<>();
    ArrayList<String> not_interested=new ArrayList<>();
    ArrayList<String> interested=new ArrayList<>();

    Event_Search(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listName.clear();
                listEvent.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    EventDetails temp = new EventDetails();
                    temp = users.getValue(EventDetails.class);
                    String key = dataSnapshot.getKey();
                    if (!temp.eventName.equals("")) {
                        listEvent.add(temp);
                        listName.add(temp.eventName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

        public void Search(final Context context) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listName.clear();
                    listEvent.clear();
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        EventDetails temp = new EventDetails();
                        temp = users.getValue(EventDetails.class);
                        String key = dataSnapshot.getKey();
                        if (!temp.eventName.equals("")) {
                            listEvent.add(temp);
                            listName.add(temp.eventName);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            final String userID = Students.current.getUid();

            status = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
            status.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    EventDetails e = new EventDetails();
                    e = dataSnapshot.getValue(EventDetails.class);
                    String key = dataSnapshot.getKey();
                    attending.add(key);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            status = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
            status.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    EventDetails e = new EventDetails();
                    e = dataSnapshot.getValue(EventDetails.class);
                    String key = dataSnapshot.getKey();
                    interested.add(key);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            status = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
            status.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    EventDetails e = new EventDetails();
                    e = dataSnapshot.getValue(EventDetails.class);
                    String key = dataSnapshot.getKey();
                    not_interested.add(key);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            attrdialog = new SpinnerDialog((Activity) context, listName, "Search");
            attrdialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String s, int i) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    View mView = layoutInflater.inflate(R.layout.after_search, null);
                    TextView text = (TextView) mView.findViewById(R.id.editText55);

                    final EventDetails temp = listEvent.get(i);
                    String string = "<b>" + temp.getEventName() + "</b> <br>";
                    string = string + temp.getEventTime() + " , " + temp.getEventDate() + "<br>";
                    string = string + temp.getEventLoc() + "<br><br>" + temp.getEventDescription();
                    builder2.setCancelable(true);
                    text.setMovementMethod(new ScrollingMovementMethod());
                    text.setText(Html.fromHtml(string));

                    final CheckBox checkBox1, checkBox2, checkBox3;
                    ImageButton add, arrow, edit;
                    checkBox1 = (CheckBox) mView.findViewById(R.id.checkBox2); // NOT GOING
                    checkBox2 = (CheckBox) mView.findViewById(R.id.checkBox3); // MAYBE
                    checkBox3 = (CheckBox) mView.findViewById(R.id.checkBox4); // GOING
                    add = mView.findViewById(R.id.addPerson);
                    edit = mView.findViewById(R.id.edit);
                    arrow = mView.findViewById(R.id.arrow);

                    if (userID.equals(temp.openerID))
                        edit.setVisibility(View.VISIBLE);
                    else
                        edit.setVisibility(View.INVISIBLE);

                    final int[] count_Attending = {0};
                    final int[] count_Not_Interested = {0};
                    final int[] count_Interested = {0};
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending");
                    mDatabase.child(temp.eventID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            count_Attending[0] = 0;
                            for (DataSnapshot users : dataSnapshot.getChildren()) {
                                count_Attending[0]++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested");
                    mDatabase.child(temp.eventID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            count_Not_Interested[0] = 0;
                            for (DataSnapshot users : dataSnapshot.getChildren()) {
                                count_Not_Interested[0]++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested");
                    mDatabase.child(temp.eventID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            count_Interested[0] = 0;
                            for (DataSnapshot users : dataSnapshot.getChildren()) {
                                count_Interested[0]++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if (attending.contains(temp.eventID))
                        checkBox3.setChecked(true);
                    else if (interested.contains(temp.eventID))
                        checkBox2.setChecked(true);
                    else if (not_interested.contains(temp.eventID))
                        checkBox1.setChecked(true);

                    checkBox3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (checkBox3.isChecked()) {
                                checkBox2.setChecked(false);
                                checkBox1.setChecked(false);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(temp.eventID);
                                interested.remove(temp.eventID);
                                mDatabase.child(userID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(temp.eventID);
                                not_interested.remove(temp.eventID);
                                mDatabase.child(userID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(temp.eventID);
                                attending.add(temp.eventID);
                                mDatabase.child(userID).setValue(true);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                                mDatabase.child(temp.eventID).setValue(temp);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();
                            } else {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();
                            }
                        }
                    });

                    checkBox1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (checkBox1.isChecked()) {
                                checkBox2.setChecked(false);
                                checkBox3.setChecked(false);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(temp.eventID);
                                interested.remove(temp.eventID);
                                mDatabase.child(userID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(temp.eventID);
                                attending.remove(temp.eventID);
                                mDatabase.child(userID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(temp.eventID);
                                not_interested.add(temp.eventID);
                                mDatabase.child(userID).setValue(true);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).setValue(temp);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();
                            } else {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();
                            }
                        }
                    });

                    checkBox2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (checkBox2.isChecked()) {
                                checkBox1.setChecked(false);
                                checkBox3.setChecked(false);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Not Interested").child(temp.eventID);
                                not_interested.remove(temp.eventID);
                                mDatabase.child(userID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Attending").child(temp.eventID);
                                attending.remove(temp.eventID);
                                mDatabase.child(userID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("countEvents").child("Interested").child(temp.eventID);
                                interested.add(temp.eventID);
                                mDatabase.child(userID).setValue(true);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).setValue(temp);

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Not_Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();
                            } else {
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
                                mDatabase.child(temp.eventID).removeValue();
                            }
                        }
                    });

                    builder2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Event_Search event_search = new Event_Search();
                            event_search.Search(context);
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            EventDetails.tName = temp.eventName;
                            EventDetails.tDate = temp.eventDate;
                            EventDetails.tTime = temp.eventTime;
                            EventDetails.tDescription = temp.eventDescription;
                            EventDetails.tEventID = temp.eventID;
                            EventDetails.tOpenerID = temp.openerID;
                            EventDetails.tLoc = temp.eventLoc;
                            Intent intent = new Intent(context, InviteActivity.class);
                            context.startActivity(intent);
                        }
                    });

                    arrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                            String string = "<b>" + temp.getEventName() + "</b> <br> <br>" + count_Attending[0] + " People Going<br>" + count_Interested[0] + " People Maybe<br>" + count_Not_Interested[0] + " People Not Going";
                            builder2.setMessage(Html.fromHtml(string));
                            builder2.setCancelable(true);

                            builder2.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder2.create();
                            alert11.show();
                        }
                    });

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventDetails.tName = temp.eventName;
                            EventDetails.tDate = temp.eventDate;
                            EventDetails.tTime = temp.eventTime;
                            EventDetails.tDescription = temp.eventDescription;
                            EventDetails.tEventID = temp.eventID;
                            EventDetails.tOpenerID = temp.openerID;
                            EventDetails.tLoc = temp.eventLoc;
                            Intent intent = new Intent(context, CreatEventsActivity.class);
                            context.startActivity(intent);
                        }
                    });

                    builder2.setView(mView);
                    AlertDialog alert11 = builder2.create();
                    alert11.setCanceledOnTouchOutside(false);
                    alert11.show();
                }
            });
            attrdialog.showSpinerDialog();
        }
}
