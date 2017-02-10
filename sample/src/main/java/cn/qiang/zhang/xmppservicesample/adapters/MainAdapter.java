package cn.qiang.zhang.xmppservicesample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;

import butterknife.ButterKnife;
import cn.qiang.zhang.xmppservicesample.R;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;

/**
 * <p>
 * Created by mrZQ on 2017/2/10.
 */
public class MainAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "MainAdapter";

    /** 链式图类型的数据 */
    private LinkedHashMap<String, String[]> expandableData = new LinkedHashMap<>();

    private Context context;

    /** 子菜单监听 */
    private OnChildItemClickListener childItemClickListener;

    /** 功能分组名 */
    private String[] functionName;

    public MainAdapter(Context context, String[] functionName, int[] functionArray) {
        this.context = context;
        this.functionName = functionName;
        // 通过分组名布置子菜单
        for (int i = 0; i < functionName.length; i++) {
            expandableData.put(functionName[i], context.getResources().getStringArray(functionArray[i]));
        }
    }

    /** 分组列表的子菜单点击监听 */
    public interface OnChildItemClickListener {
        void onItemClick(int groupPosition, int childPosition);
    }

    public void setChildItemClickListener(OnChildItemClickListener childItemClickListener) {
        this.childItemClickListener = childItemClickListener;
    }

    @Override
    public int getGroupCount() {
        if (functionName != null) {
            return functionName.length;
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            String funcKey = functionName[groupPosition];
            return expandableData.get(funcKey).length;
        } catch (Exception e) {
            // no-op
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (functionName != null) {
            return functionName[groupPosition];
        }
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        try {
            return expandableData.get(functionName[groupPosition])[childPosition];
        } catch (Exception e) {
            // no-op
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // 对于LayoutInflater形式创建View实例的inflate方法参数说明：
            // 1.布局文件 + null表示只创建实例，不初始化布局属性和添加到任何容器
            // 2.布局文件+容器+false 表示创建实例并得到容器的布局属性
            // 3.如果第三个参数是true则表示这个实例会添加到容器中，返回这个容器
            // 因此选第2种是最佳方案
            view = LayoutInflater.from(context).inflate(R.layout.main_layout_group, parent, false);
        }
        try {
            String groupName = functionName[groupPosition];
            TextView groupTitle = ButterKnife.findById(view, R.id.group_item_title);
            groupTitle.setText(groupName);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.main_layout_child, parent, false);
        }
        TextView childTitle = ButterKnife.findById(view, R.id.child_item_title);
        try {
            String childName = expandableData.get(functionName[groupPosition])[childPosition];
            childTitle.setText(childName);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childItemClickListener != null) {
                        childItemClickListener.onItemClick(groupPosition, childPosition);
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
