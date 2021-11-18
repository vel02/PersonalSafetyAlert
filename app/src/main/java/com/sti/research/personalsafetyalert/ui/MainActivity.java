package com.sti.research.personalsafetyalert.ui;

import static com.sti.research.personalsafetyalert.util.Constants.*;
import static com.sti.research.personalsafetyalert.util.api.SmsApi.SLOT_SIM_ONE;
import static com.sti.research.personalsafetyalert.util.api.SmsApi.SLOT_SIM_TWO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.sti.research.personalsafetyalert.BaseActivity;
import com.sti.research.personalsafetyalert.R;
import com.sti.research.personalsafetyalert.adapter.view.contact.ContactRecyclerAdapter;
import com.sti.research.personalsafetyalert.adapter.view.message.MessageRecyclerAdapter;
import com.sti.research.personalsafetyalert.databinding.ActivityMainBinding;
import com.sti.research.personalsafetyalert.model.Message;
import com.sti.research.personalsafetyalert.model.list.Contact;
import com.sti.research.personalsafetyalert.receiver.sms.SentReceiverObserver;
import com.sti.research.personalsafetyalert.receiver.sms.SmsDeliveredReceiver;
import com.sti.research.personalsafetyalert.receiver.sms.SmsSentReceiver;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragment;
import com.sti.research.personalsafetyalert.ui.screen.contact.ContactFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragment;
import com.sti.research.personalsafetyalert.ui.screen.home.HomeFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.menu.help.HelpActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.notworking.NotWorkingActivity;
import com.sti.research.personalsafetyalert.ui.screen.menu.settings.SettingsActivity;
import com.sti.research.personalsafetyalert.ui.screen.message.MessageFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragment;
import com.sti.research.personalsafetyalert.ui.screen.permission.PermissionFragmentDirections;
import com.sti.research.personalsafetyalert.ui.screen.visual.VisualMessageFragmentDirections;
import com.sti.research.personalsafetyalert.util.Constants;
import com.sti.research.personalsafetyalert.util.Utility;
import com.sti.research.personalsafetyalert.util.api.AudioRecordManager;
import com.sti.research.personalsafetyalert.util.api.SmsApi;
import com.sti.research.personalsafetyalert.util.screen.contact.ContactStoreSinglePerson;
import com.sti.research.personalsafetyalert.util.screen.contact.SelectPreferredContactPreference;
import com.sti.research.personalsafetyalert.util.screen.home.HomeCustomMessagePreference;
import com.sti.research.personalsafetyalert.util.screen.main.UsernamePreference;
import com.sti.research.personalsafetyalert.util.screen.manager.WaitResultManager;
import com.sti.research.personalsafetyalert.util.screen.sms.SmsSimSubscriptionPreference;
import com.sti.research.personalsafetyalert.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.BuildConfig;

public class MainActivity extends BaseActivity implements
        Observer,
        HostScreen,
        NavigatePermission,
        LocationServiceListener,
        BaseActivity.OnDataProcessingListener,
        MessageRecyclerAdapter.OnMessageClickListener,
        ContactRecyclerAdapter.OnContactClickListener {

    private static final String TAG = "test";

    private com.sti.research.personalsafetyalert.model.single.Contact contact;
    private List<Contact> contacts = new ArrayList<>();
    private List localList;

    private static final String DEFAULT_MESSAGE = ": https://www.google.com/maps/search/?api=1&query=";

    private String userMessage;
    private String audioPath;

    private String contactList;
    private String emailList;


    private Location location;


    @Override
    public void onDataProcessing(Location location) {
        Log.d(TAG, "MAIN ACTIVITY LOCATION: " + location.getLatitude() + " - " + location.getLongitude());
        SubscriptionInfo simInfo = (SubscriptionInfo) localList.get(SLOT_SIM_ONE);

        //location
        this.location = location;

        //user message received here...
        this.userMessage = HomeCustomMessagePreference.getInstance().getCustomMessageStorage(this);
        Log.d(TAG, "MAIN ACTIVITY CUSTOM MESSAGE: " + this.userMessage);


        //Contact
        String preferredContact = SelectPreferredContactPreference.getInstance().getSelectPreferredContact(this);
        Log.d(TAG, "MAIN ACTIVITY PREFERRED CONTACT: " + preferredContact);
        switch (preferredContact) {
            case "SINGLE_CONTACT":
                Log.d(TAG, "MAIN ACTIVITY PREFERRED CONTACT SELECTED: Send to one specific contact");
                contact = ContactStoreSinglePerson.getInstance().restoreContactSinglePerson(this);
                Log.d(TAG, "MAIN ACTIVITY CONTACT DISPLAY: " + contact);


                //Record Audio for attachment...
                AudioRecordManager.getInstance(AudioRecordManager.AUDIO_TEST_DURATION, AudioRecordManager.AUDIO_INTERVAL, (path, filename) -> {
                    MainActivity.this.audioPath = path;
                    Log.d(TAG, "MAIN ACTIVITY AUDIO PATH: " + path);


                    //send sms

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                        return;


                    if (subscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                        SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                .sendTo(contact.getNumber(), this.userMessage, SLOT_SIM_ONE, sentPI, deliveredPI);

                        //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                        SmsSimSubscriptionPreference.settings()
                                .setSmsSimSubscriptionStatus(this,
                                        SmsSimSubscriptionPreference.FROM_SIM_ONE_FAILED_STATUS);
                    } else {
                        Log.d(TAG, "FIRST SEND TEXT");
                        SmsApi.getInstance(location.getLatitude(), location.getLongitude())
                                .sendTo(contact.getNumber(), this.userMessage, sentPI, deliveredPI);

                        //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                        SmsSimSubscriptionPreference.settings()
                                .setSmsSimSubscriptionStatus(this,
                                        SmsSimSubscriptionPreference.FROM_SINGLE_SIM_FAILED_STATUS);
                    }

                    //send email
                    viewModel.sendEmail("ACCIDENT REPORT - Personal Safety App Team",
                            generateMessage(simInfo.getNumber(), location),
                            contact.getEmail(), path, filename);

                }, "PersonalSafety").start();


                break;
            case "MULTIPLE_CONTACT":
                Log.d(TAG, "MAIN ACTIVITY PREFERRED CONTACT SELECTED: Send to this list of contacts");
                StringBuilder contactBuilder = new StringBuilder();
                StringBuilder emailBuilder = new StringBuilder();
                for (int i = 0; i < this.contacts.size(); i++) {
                    Contact contact = this.contacts.get(i);
                    Log.d(TAG, "MAIN ACTIVITY LIST CONTACT #" + i + ": " + contact);
                    contactBuilder.append(contact.getMobileNumber()).append(",");
                    emailBuilder.append(contact.getEmail()).append(",");
                }


                contactList = contactBuilder.substring(0, contactBuilder.length() - 1);
                emailList = emailBuilder.substring(0, emailBuilder.length() - 1);

                Log.d(TAG, "LIST OF CONTACT NUMBERS: " + contactList);
                Log.d(TAG, "LIST OF CONTACTS EMAILS: " + emailList);


                //Record Audio for attachment...
                AudioRecordManager.getInstance(AudioRecordManager.AUDIO_TEST_DURATION, AudioRecordManager.AUDIO_INTERVAL, (path, filename) -> {
                    MainActivity.this.audioPath = path;
                    Log.d(TAG, "MAIN ACTIVITY AUDIO PATH: " + path);
                    //send sms
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                        return;


                    if (subscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                        SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                .sendToMany(contactList, this.userMessage, SLOT_SIM_ONE, sentPI, deliveredPI);

                        //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                        SmsSimSubscriptionPreference.settings()
                                .setSmsSimSubscriptionStatus(this,
                                        SmsSimSubscriptionPreference.FROM_SIM_ONE_FAILED_STATUS);
                    } else {
                        SmsApi.getInstance(location.getLatitude(), location.getLongitude())
                                .sendToMany(contactList, this.userMessage, sentPI, deliveredPI);

                        //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                        SmsSimSubscriptionPreference.settings()
                                .setSmsSimSubscriptionStatus(this,
                                        SmsSimSubscriptionPreference.FROM_SINGLE_SIM_FAILED_STATUS);
                    }

                    //send email
                    viewModel.sendEmail("ACCIDENT REPORT - Personal Safety App Team",
                            generateMessage(simInfo.getNumber(), location),
                            emailList, path, filename);

                }, "PersonalSafety").start();

                break;
        }


    }

    private String generateMessage(String mobileNumber, Location location) {
        String name = UsernamePreference.getInstance().getUsernameInput(this);
        if (name.isEmpty()) name = "Anonymous";
        return userMessage
                + "\n\nYou can reach " + name + " with his/her contact details:"
                + "\n" + "Mobile Number: " + mobileNumber
                + "\n\n" + name + "'s current location" + DEFAULT_MESSAGE
                + location.getLatitude() + "," + location.getLongitude()
                + "\n\nBelow is an audio attachment that gives you more information regarding "
                + name + "'s current surroundings.";
    }

    private void initSmsSubscriptionManager() {
        this.sentPI = PendingIntent.getBroadcast(
                this, 0,
                new Intent(this, SmsSentReceiver.class),
                0);

        this.deliveredPI = PendingIntent.getBroadcast(
                this, 0,
                new Intent(this, SmsDeliveredReceiver.class),
                0);

        this.subscriptionManager = SubscriptionManager.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return;
        this.localList = subscriptionManager.getActiveSubscriptionInfoList();


        SentReceiverObserver.getInstance().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object sentReceiverResult) {

        String preferredContact = SelectPreferredContactPreference.getInstance().getSelectPreferredContact(this);

        if (sentReceiverResult != null && sentReceiverResult.equals("GENERIC_FAILURE")) {


            switch (SmsSimSubscriptionPreference.settings().getSmsSimSubscriptionStatus(this)) {
                case SmsSimSubscriptionPreference.FROM_SIM_ONE_FAILED_STATUS:

                    if (preferredContact.equals("SINGLE_CONTACT")) {
                        //FAILED SIM ONE, THIS WILL USED TO RESEND VIA SIM TWO.
                        SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                .sendTo(contact.getNumber(), userMessage, SLOT_SIM_TWO, sentPI, deliveredPI);
                    } else if (preferredContact.equals("MULTIPLE_CONTACT")) {
                        //FAILED SIM ONE, THIS WILL USED TO RESEND VIA SIM TWO.
                        SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                .sendToMany(contactList, userMessage, SLOT_SIM_TWO, sentPI, deliveredPI);
                    }

                    //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                    SmsSimSubscriptionPreference.settings()
                            .setSmsSimSubscriptionStatus(this,
                                    SmsSimSubscriptionPreference.FROM_SIM_TWO_FAILED_STATUS);
                    break;

                case SmsSimSubscriptionPreference.FROM_SINGLE_SIM_FAILED_STATUS:
                    Log.d(TAG, "FAILED SINGLE SIM SEND");
                    //FAILED SINGLE SIM, REUSED THIS TO REQUEST A LOAD FROM SIM ONE.
                case SmsSimSubscriptionPreference.FROM_SIM_TWO_FAILED_STATUS:
                    Log.d(TAG, "REQUEST LOAN");
                    //FAILED SIM TWO, WE USED THIS TO REQUEST A LOAD FROM SIM ONE.
                    SubscriptionInfo simInfo = (SubscriptionInfo) localList.get(SLOT_SIM_ONE);
                    Log.d(TAG, "MOBILE NUMBER: " + simInfo.getNumber());
                    SmsApi.getInstance().requestLoad(simInfo.getNumber(), sentPI, deliveredPI);

                    //SAVE STATE TO PROCEED TO REQUESTING A LOAD.
                    SmsSimSubscriptionPreference.settings()
                            .setSmsSimSubscriptionStatus(this,
                                    SmsSimSubscriptionPreference.FROM_SIM_REQUESTED_LOAD_STATUS);
                    break;

                case SmsSimSubscriptionPreference.FROM_SIM_REQUESTED_LOAD_STATUS:
                    //SIM ONE USED FOR RESEND MESSAGE AFTER REQUEST LOAD.

                    //WILL WAIT FOR AT LEAST 20 SECONDS FOR PROCESSING A LOAD REQUESTED BEFORE RESEND.
                    new WaitResultManager(20000, WaitResultManager.WAIT_INTERVAL, () -> {

                        if (preferredContact.equals("SINGLE_CONTACT")) {
                            SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                    .sendTo(contact.getNumber(), userMessage, SLOT_SIM_ONE, sentPI, deliveredPI);
                        } else if (preferredContact.equals("MULTIPLE_CONTACT")) {
                            SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                    .sendToMany(contactList, userMessage, SLOT_SIM_ONE, sentPI, deliveredPI);
                        }

                        //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                        SmsSimSubscriptionPreference.settings()
                                .setSmsSimSubscriptionStatus(this,
                                        SmsSimSubscriptionPreference.FROM_SIM_ONE_RESEND_FAILED_STATUS);
                    }).start();
                    break;

                case SmsSimSubscriptionPreference.FROM_SIM_ONE_RESEND_FAILED_STATUS:
                    Log.d(TAG, "RESEND MESSAGE AFTER REQUEST LOAD FAILED.");
                    break;
            }

        } else if (sentReceiverResult != null && sentReceiverResult.equals("SMS_SENT")) {
            Log.d(TAG, "SMS_SENT FOR REQUEST LOAN");

            switch (SmsSimSubscriptionPreference.settings().getSmsSimSubscriptionStatus(this)) {
                case SmsSimSubscriptionPreference.FROM_SIM_REQUESTED_LOAD_STATUS:
                    Log.d(TAG, "NOW REQUESTING... WAIT FOR 20 SECONDS TO SEND");
                    //SIM ONE USED FOR RESEND MESSAGE AFTER REQUEST LOAD.

                    //WILL WAIT FOR AT LEAST 20 SECONDS FOR PROCESSING A LOAD REQUESTED BEFORE RESEND.
                    new WaitResultManager(20000, WaitResultManager.WAIT_INTERVAL, () -> {

                        if (preferredContact.equals("SINGLE_CONTACT")) {
                            SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                    .sendTo(contact.getNumber(), userMessage, sentPI, deliveredPI);
                        } else if (preferredContact.equals("MULTIPLE_CONTACT")) {
                            SmsApi.getInstance(localList, location.getLatitude(), location.getLongitude())
                                    .sendToMany(contactList, userMessage, sentPI, deliveredPI);
                        }

                        //SAVE STATE IF EVER SENT FAILED TO THE CURRENT SIM.
                        SmsSimSubscriptionPreference.settings()
                                .setSmsSimSubscriptionStatus(this,
                                        SmsSimSubscriptionPreference.FROM_SIM_ONE_RESEND_FAILED_STATUS);
                    }).start();
                    break;
            }

        }


    }

    @Override
    public void requestNotificationLocation() {
        this.locationService.requestNotificationLocation();
    }

    @Override
    public void removeNotificationLocation() {
        this.locationService.removeNotificationLocation();
    }

    @Override
    public void onMessageResult(Message message) {
        assert navHostFragment != null;
        if ((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof HomeFragment)) {
            HomeFragment fragment = (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            fragment.onMessageDataReceiver(message);
        }
    }

    @Override
    public void onContactResult(Contact contact) {
        assert navHostFragment != null;
        if ((navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof ContactFragment)) {
            ContactFragment fragment = (ContactFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            fragment.onContactDataReceiver(contact);
        }
    }

    @Inject
    ViewModelProviderFactory providerFactory;

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private SubscriptionManager subscriptionManager;

    private PendingIntent sentPI;
    private PendingIntent deliveredPI;

    private void getIntentObject() {
        Constants.TransitionType type = (Constants.TransitionType) getIntent().getSerializableExtra(Constants.KEY_ANIM_TYPE);
        if (this.viewModel.observedTransitionType().getValue() == null)
            this.viewModel.setTransitionType(type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //permission to use fade transition
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        viewModel = new ViewModelProvider(MainActivity.this, providerFactory).get(MainViewModel.class);
        registerDataProcessingListener(this);
        getIntentObject();
        initController();
        initSmsSubscriptionManager();
        subscribeObservers();
    }

    @SuppressLint("RtlHardcoded")
    private void subscribeObservers() {
        viewModel.observedTransitionType().observe(this, transitionType -> {
            if (transitionType == TransitionType.Fade) {
                Fade enterTransition = new Fade();
                enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                getWindow().setEnterTransition(enterTransition);
            }
        });

        viewModel.observedContacts().observe(this, contacts -> {
            if (contacts != null) {
                this.contacts = contacts;
            }
        });
    }

    private void initController() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_permission
                    || destination.getId() == R.id.nav_message
                    || destination.getId() == R.id.nav_visual_message
                    || destination.getId() == R.id.nav_contact
            ) {
                //noinspection deprecation
                binding.appBarLayout.setTargetElevation(0F);
                if (destination.getId() == R.id.nav_message
                        || destination.getId() == R.id.nav_visual_message
                        || destination.getId() == R.id.nav_contact
                ) {
                    binding.toolbar.setTitle("");
                } else if (destination.getId() == R.id.nav_home) {
                    binding.toolbar.setTitle(R.string.app_name);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        } else if (item.getItemId() == R.id.action_not_working) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, NotWorkingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_ANIM_TYPE, TransitionType.Fade);
            startActivity(intent, options.toBundle());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInflate(View view, String screen) {

        NavDirections directions;
        final String content;

        switch (screen) {
            case "tag_fragment_contact":
                directions = HomeFragmentDirections.actionNavHomeToNavContact();
                break;

            case "tag_fragment_message":
                directions = HomeFragmentDirections.actionNavHomeToNavMessage();
                break;

            case "tag_fragment_visual_message":
                content = viewModel.getSelectedMessage();
                directions = HomeFragmentDirections.actionNavHomeToNavVisualMessage(content);
                break;

            case "tag_fragment_message_to_visual":
                content = viewModel.getAddMessage();
                directions = MessageFragmentDirections.actionNavMessageToNavVisualMessage(content);
                break;

            case "tag_fragment_message_to_home":
                directions = MessageFragmentDirections.actionNavMessageToNavHome();
                break;

            case "tag_fragment_visual_to_home":
                directions = VisualMessageFragmentDirections.actionNavVisualMessageToNavHome();
                break;

            case "tag_fragment_home_to_permission":
                directions = HomeFragmentDirections.actionNavHomeToNavPermission();
                break;

            case "tag_fragment_permission_to_home":
                directions = PermissionFragmentDirections.actionNavPermissionToNavHome();
                break;

            case "tag_fragment_contact_to_add_contact":
                directions = ContactFragmentDirections.actionNavContactToAddContactFragment();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + screen);
        }

        Navigation.findNavController(view).navigate(directions);
    }

    @Override
    public void onInflate(View view, String screen, Object object) {

        NavDirections directions = null;

        switch (screen) {
            case "tag_fragment_contact_to_update_contact":
                if (object instanceof Contact)
                    directions = ContactFragmentDirections.actionNavContactToNavUpdateContact((Contact) object);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + screen);
        }

        assert directions != null;
        Navigation.findNavController(view).navigate(directions);

    }

    @Override
    public boolean checkLocationPermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public boolean checkSendSMSPermission() {
        int hasReadPermissionSendSms = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int hasReadPermissionPhoneState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);

        return hasReadPermissionSendSms != PackageManager.PERMISSION_GRANTED
                || hasReadPermissionPhoneState != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean checkRecordAudioPermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public boolean checkStoragePermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return hasReadPermission == PackageManager.PERMISSION_DENIED;
    }

    @Override
    public void requestLocationPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_location_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_NETWORK_STATE},
                                    PermissionManager.PERMISSION_LOCATION_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE},
                    PermissionManager.PERMISSION_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void requestSendSMSPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)
                && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_send_sms_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS,
                                            Manifest.permission.READ_PHONE_STATE},
                                    PermissionManager.PERMISSION_SEND_SMS_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_PHONE_STATE},
                    PermissionManager.PERMISSION_SEND_SMS_REQUEST_CODE);
        }
    }

    @Override
    public void requestRecordAudioPermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_audio_record_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    PermissionManager.PERMISSION_RECORD_AUDIO_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PermissionManager.PERMISSION_RECORD_AUDIO_REQUEST_CODE);
        }
    }

    @Override
    public void requestStoragePermission() {
        boolean shouldProvideRational = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (shouldProvideRational) {
            Snackbar.make(
                    binding.getRoot(), R.string.txt_storage_permission_rational,
                    Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                    .setActionTextColor(getResources().getColor(R.color.primaryDark))
                    .setAction(R.string.action_ok, v ->
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PermissionManager.PERMISSION_STORAGE_REQUEST_CODE)).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionManager.PERMISSION_STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.PERMISSION_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                viewModel.setPermissionLocationState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else if (requestCode == PermissionManager.PERMISSION_SEND_SMS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setPermissionSendSMSState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else if (requestCode == PermissionManager.PERMISSION_RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setPermissionRecordAudioState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else if (requestCode == PermissionManager.PERMISSION_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setPermissionStorageState(PackageManager.PERMISSION_GRANTED);
            } else {
                Snackbar.make(
                        binding.getRoot(),
                        R.string.txt_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getResources().getColor(R.color.primaryLight))
                        .setActionTextColor(getResources().getColor(R.color.primaryDark))
                        .setAction(R.string.action_settings, view -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }).show();
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    public void onBackPressed() {
        assert navHostFragment != null;
        if (!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof PermissionFragment)) {
            super.onBackPressed();
        }
    }

}