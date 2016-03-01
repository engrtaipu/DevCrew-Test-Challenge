# DevCrew-Test-Challenge
Q 1) What is the major difference between an abstract class and an interface?
Ans:
1.	Main difference is methods of a Java interface are implicitly abstract and cannot have implementations. A Java abstract class can have instance methods that implement a default behavior.
2.	Variables declared in a Java interface is by default final. An abstract class may contain non-final variables.
3.	Members of a Java interface are public by default. A Java abstract class can have the usual flavors of class members like private, protected, etc..
4.	Java interface should be implemented using keyword “implements”; A Java abstract class should be extended using keyword “extends”.
5.	An interface can extend another Java interface only; an abstract class can extend another Java class or abstract class and implement multiple Java interfaces.
6.	A Java class can implement multiple interfaces but it can extend only one abstract class.
7.	Interface is absolutely abstract and cannot be instantiated; A Java abstract class also cannot be instantiated, but can be invoked if a main() exists.
8.	In comparison with java abstract classes, java interfaces are slow as it requires extra indirection.

Q 2) Why is Java 7’s class inheritance flawed?
Ans:
Java doesn’t support multiple class inheritance but it does support multiple interface inheritance and an interface is not a class.
Q3) what are the major differences between Activities and fragments?
Ans:
1)	A fragment cannot exist independently it must be a part of the hosting activity whereas an activity can exist independently.
2)	An activity is placed into a back stack of activities that's managed by the system when it's stopped, by default. However, a fragment is placed into a back stack managed by the host activity only when you explicitly request that the instance be saved by calling addToBackStack() during a transaction that removes the fragment.
3)	An Activity is an application component that provides a screen, with which users can interact in order to do something. Whereas a Fragment represents a behavior or a portion of user interface in an Activity.
Q 4) when using Fragments, how do you communicate back to their hosting Activity?
Ans:
One can use interface as a call back to the activity hosting the fragment.
Q 5) Can you make an entire app without ever using Fragments? Why or why not?
Are there any special cases when you absolutely have to use or should use
Fragments?
Ans:
Yes one can make an entire app without using a single fragment. Because fragment are just components of the activity and an activity can exist independently.
The use of fragments is encouraged when the app is design to support tablets which have a larger screen size then phone for the purpose to make use of the extra space available.
And fragments are also encouraged to be used in the case to design complex UI layout.
Q 6) what makes an AsyncTask such an annoyance to Android developers? Detail
Some of the issues with AsyncTask , and how to potentially solve them.
Ans:
Starting an AsyncTask can pose problems if your Activities and Fragments are randomly getting recreated, so you will have to pay special attention to how you handle them. There are a few dangerous things that could happen with improperly handled AsyncTasks: memory leaks and crashes.
Memory Leaks
Memory leaks can occur if your AsyncTask holds on to a reference to an Activity or a Fragment. When Android destroys your Activity or Fragment because of an orientation change (or any other configuration change), it will not destroy any AsyncTasks that you started. So if an AsyncTask has a reference to a now-destroyed Activity or Fragment, the garbage collector won’t be able to collect that Activity or Fragment even though it should never be used again. You are particularly susceptible to memory leaks if you have an AsyncTask declared as a non-static inner class of an Activity or Fragment because that AsyncTask will implicitly hold a reference to its parent class (in this case the Activity or Fragment) even though it appears as though it doesn’t have this reference.
IllegalArgumentExceptions
The other problem you can run into is an actual crash. If, for example, you are displaying a ProgressDialog in the onPreExecute method of your AsyncTask and dismissing it (calling ProgressDialog.dismiss()) in the onPostExecute or onCancelled method a few things will happen.
f you rotate the device before the task is finished you’ll immediately see a WindowLeaked exception printed out to the logs. This indicates that Android couldn’t release the resources for that window (your ProgressDialog) because you are still referencing it in your AsyncTask. This exception won’t actually cause a crash. However, when the task eventually does complete, and you call ProgressDialog.dismiss(), you will get an IllegalArgumentException: View…not attached to window manager. This is indicating that you are trying to dismiss a dialog that’s not actually attached to anything and it will cause a crash.
Solutions:
Cancelling AsyncTasks
One way to avoid this is to cancel AsyncTasks in your Activity or Fragment’s onDestroy method. You can also save the state of your task (if it’s running or not) in the onSaveInstanceState method. When your Activity or Fragment is recreated, you can use that to determine if you need to restart the task.

private static final String STATE_TASK_RUNNING = "taskRunning";

private MyTask mTask;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(...);
    
    // Setup views
    ...
    
    // Check if the task was running so we can restart it
    if (savedInstanceState != null) {
        if (savedInstanceState.getBoolean(STATE_TASK_RUNNING, false)) {
            mTask = new MyTask();
            mTask.execute(...);
        }
    }
}

/**
 * Suppose this was a click handler for something
 */
public void startTaskClicked(View view) {
    // We can start the task now
    mTask = new MyTask();
    mTask.execute(...);
}

private boolean isTaskRunning() {
    return (mTask != null) && (mTask.getStatus() == AsyncTask.Status.RUNNING);
}

@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    
    // If the task is running, save it in our state
    if (isTaskRunning()) {
        outState.putBoolean(STATE_TASK_RUNNING, true);
    }
}

@Override
protected void onDestroy() {
    super.onDestroy();
    
    // Cancel the task if it's running
    if (isTaskRunning()) {
        mTask.cancel(true);
    }
}

private class MyTask extends AsyncTask<...> {
    ...
}
Using a Retained Fragment

Now, it is not always an option (or at least a good option) to cancel and restart your AsyncTasks whenever an orientation change occurs. For example if you have a task that is downloading a file and it’s almost done when suddenly the user rotates the device, it would be an extremely unpleasant user experience for the task to be cancelled then restarted after that. This brings us back to something I mentioned earlier: calling setRetainInstance(true) on a Fragment. As I had described, setRetainInstance(true) tells Android to not destroy a Fragment when a configuration change happens. If you use a retained Fragment to host your AsyncTask, you can avoid ever having to restart your tasks. There are still a few things you should keep in mind though. Your retained Fragment should have no UI. Instead, you can declare an interface that your Activity will implement and that your Fragment will use to tell the Activity to update the UI (or anything else). In onAttach, you should cast your Activity to that interface and save it in a listener member variable. In onDetach you must set that listener variable to null, so you don’t leak an Activity reference. In the AsyncTask callbacks (onPreExecute, onProgressUpdate, onPostExecute and onCancelled), you should make sure the listener isn’t null then trigger the appropriate callback on the listener and let it handle the rest. Here’s an example of how to implement this:

public class NetworkRequestFragment extends Fragment {
    
    // Declare some sort of interface that your AsyncTask will use to communicate with the Activity
    public interface NetworkRequestListener {
        void onRequestStarted();
        void onRequestProgressUpdate(int progress);
        void onRequestFinished(SomeObject result);
    }
    
    private NetworkTask mTask;
    private NetworkRequestListener mListener;
    
    private SomeObject mResult;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // Try to use the Activity as a listener
        if (activity instanceof NetworkRequestListener) {
            mListener = (NetworkRequestListener) activity;
        } else {
            // You can decide if you want to mandate that the Activity implements your callback interface
            // in which case you should throw an exception if it doesn't:
            throw new IllegalStateException("Parent activity must implement NetworkRequestListener");
            // or you could just swallow it and allow a state where nobody is listening
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Retain this Fragment so that it will not be destroyed when an orientation
        // change happens and we can keep our AsyncTask running
        setRetainInstance(true);
    }
    
    /**
     * The Activity can call this when it wants to start the task
     */
    public void startTask(String url) {
        mTask = new NetworkTask(url);
        mTask.execute();
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // If the AsyncTask finished when we didn't have a listener we can
        // deliver the result here
        if ((mResult != null) && (mListener != null)) {
            mListener.onRequestFinished(mResult);
            mResult = null;
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        // We still have to cancel the task in onDestroy because if the user exits the app or
        // finishes the Activity, we don't want the task to keep running
        // Since we are retaining the Fragment, onDestroy won't be called for an orientation change
        // so this won't affect our ability to keep the task running when the user rotates the device
        if ((mTask != null) && (mTask.getStatus == AsyncTask.Status.RUNNING)) {
            mTask.cancel(true);
        }
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        
        // This is VERY important to avoid a memory leak (because mListener is really a reference to an Activity)
        // When the orientation change occurs, onDetach will be called and since the Activity is being destroyed
        // we don't want to keep any references to it
        // When the Activity is being re-created, onAttach will be called and we will get our listener back
        mListener = null;
    }
    
    private class NetworkTask extends AsyncTask<String, Integer, SomeObject> {
        
        @Override
        protected void onPreExecute() {
            if (mListener != null) {
                mListener.onRequestStarted();
            }
        }
        
        @Override
        protected SomeObject doInBackground(String... urls) {
           // Make the network request
           ...
           // Whenever we want to update our progress:
           publishProgress(progress);
           ...
           return result;
        }
        
        @Override
        protected void onProgressUpdate(Integer... progress) {
            if (mListener != null) {
                mListener.onRequestProgressUpdate(progress[0]);
            }
        }
        
        @Override
        protected void onPostExecute(SomeObject result) {
            if (mListener != null) {
                mListener.onRequestFinished(result);
            } else {
                // If the task finishes while the orientation change is happening and while
                // the Fragment is not attached to an Activity, our mListener might be null
                // If you need to make sure that the result eventually gets to the Activity
                // you could save the result here, then in onActivityCreated you can pass it back
                // to the Activity
                mResult = result;
            }
        }
        
    }
}
