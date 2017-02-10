package cn.qiang.zhang.xmppservicesample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qiang.zhang.xmppservicesample.R;
import cn.qiang.zhang.xmppservicesample.activities.XmppConnectionActivity;
import cn.qiang.zhang.xmppservicesample.adapters.MainAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String ACTION_CONNECTION = TAG + "connection";
    public static final String ACTION_SIGN_UP = TAG + "sign_up";
    public static final String ACTION_SIGN_IN = TAG + "sign_in";
    public static final String ACTION_DISCONNECTION = TAG + "disconnection";

    public static final String ACTION_FRIEND_LIST = TAG + "friend_list";
    public static final String ACTION_ADD_FRIEND = TAG + "add_friend";
    public static final String ACTION_CHANGED_FRIEND = TAG + "changed_friend";
    public static final String ACTION_MOVE_FRIEND = TAG + "move_friend";
    public static final String ACTION_DELETE_FRIEND = TAG + "delete_friend";

    public static final String ACTION_SEND_NORMAL = TAG + "send_normal";
    public static final String ACTION_PENDING = TAG + "pending";
    public static final String ACTION_GET_OFF_LINE = TAG + "get_off_line";
    public static final String ACTION_PACKET = TAG + "packet";

    public static final String ACTION_IQ = TAG + "iq";
    public static final String ACTION_SUB = TAG + "subscription";
    public static final String ACTION_RELEASE = TAG + "release";
    public static final String ACTION_NOTIFI = TAG + "notification";
    public static final String ACTION_TEST = TAG + "test_function";

    @BindView(R.id.main_layout_list)
    ExpandableListView expandableListView;

    private MainAdapter adapter;

    private int[] functionArray = {R.array.xmpp_login, R.array.xmpp_roster, R.array.xmpp_message, R.array.xmpp_other};

    private String[] functionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("Home Page");
        init();
    }

    private void init() {
        functionName = getResources().getStringArray(R.array.xmpp_demo_function);
        adapter = new MainAdapter(this, functionName, functionArray);
        adapter.setChildItemClickListener(new MainAdapter.OnChildItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition) {
                switch (groupPosition) {
                    case 0: // 连接、断开、注册、登录
                        if (childPosition == 0) {
                        } else if (childPosition == 1) {

                        }
                        break;
                    case 1: // 好友分组列表、增删改查好友
                        break;
                    case 2: // 发送普通消息、发送挂起消息、接收离线消息、上传特殊消息
                        break;
                    case 3: // IQ、订阅、发布、通知、其他测试功能
                        break;
                    default:
                        break;
                }
            }
        });
        expandableListView.setAdapter(adapter);
    }

}
