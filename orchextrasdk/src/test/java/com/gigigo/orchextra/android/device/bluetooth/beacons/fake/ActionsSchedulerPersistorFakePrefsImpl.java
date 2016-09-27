package com.gigigo.orchextra.android.device.bluetooth.beacons.fake;

import android.content.Context;
import android.content.SharedPreferences;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerPersistor;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ActionsSchedulerPersistorFakePrefsImpl implements ActionsSchedulerPersistor {

  private final SharedPreferences prefReader;
  private final SharedPreferences.Editor prefEditor;

  public ActionsSchedulerPersistorFakePrefsImpl(Context context) {
    prefReader = context.getSharedPreferences("actionsScheduler", Context.MODE_PRIVATE);
    prefEditor = prefReader.edit();
  }


  @Override public ScheduledAction getScheduledActionWithId(String id) {
     Set<String> actionData = prefReader.getStringSet(id, valuesForActionData(null));
    return prefActionToScheduledAction(actionData);
  }

  @Override public void addAction(final ScheduledAction action) {
    Set<String> values = valuesForActionData(action);
    prefEditor.putStringSet(action.getId(), values);
    prefEditor.apply();
  }

  @Override public List<ScheduledAction> obtainAllPendingActions() {
    List<ScheduledAction> scheduledActionsList = new ArrayList<>();
    Map<String, Set<String>> actions = (Map<String, Set<String>>) prefReader.getAll();
    for (Set<String> actionData: actions.values()){
      scheduledActionsList.add(prefActionToScheduledAction(actionData));
    }
    return scheduledActionsList;
  }

  @Override public void removeAction(ScheduledAction action) {
    prefEditor.remove(action.getId());
    prefEditor.apply();
  }

  private Set<String> valuesForActionData(ScheduledAction action) {
    final String id = (action==null)? "0" : action.getId();
    final String time = (action==null)? "0" : String.valueOf(action.getScheduleTime());
    final String isCancelable = (action==null)? String.valueOf(false) :
        String.valueOf(action.getScheduleTime());

    return (Set<String>) Collections.unmodifiableCollection(new HashSet<String>(){{
      add(id);
      add(time);
      add(isCancelable);
    }});
  }

  private ScheduledAction prefActionToScheduledAction(Set<String> actionData) {
    Schedule schedule = new Schedule(false, 30000);
    BasicAction action = new BasicAction.ActionBuilder("1001", "10", ActionType.BROWSER, "www.marca.es", null, schedule).build();
    return action.getScheduledAction();
  }

}
