package com.example.haoza.appsclub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.MemberAdapter;
import com.example.haoza.appsclub.customObject.Department;
import com.example.haoza.appsclub.customObject.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 花名册
 */
public class ClubMemberFragment extends Fragment {
    private User user=BmobUser.getCurrentUser(User.class);
    private RecyclerView c_member_rec_view;
    private List<User> userList;
    private View view;
    private List<Department> departments;

    private List<User> J_users;//技术部
    private List<User> M_users;//秘书部
    private List<User> W_users;//外联部
    private List<User> X_users;//宣传部
    private List<User> Z_users;//组织部

    private List<List<User>> userlist_List=new ArrayList<List<User>>();
    private BmobQuery<User> eq1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.c_member_rec_frag_layout, null);
        initMember();
        initView(view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        c_member_rec_view.setLayoutManager(layoutManager);

        return view;
    }


    private void initMember() {
        //条件1:
        eq1 = new BmobQuery<User>();
        eq1.addWhereGreaterThanOrEqualTo("createdAt",user.getCreatedAt());//当前User创建时间及以后
        DepartmentQuery();
    }
    /**
     * 查询
     * Department
     */
    private void DepartmentQuery() {
        BmobQuery<Department> departmentBmobQuery = new BmobQuery<Department>();
        departmentBmobQuery.findObjects(new FindListener<Department>() {
            @Override
            public void done(List<Department> object, BmobException e) {
                if (e == null) {
                    departments = object;
                    J_UserQuery();
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    /**
     * 查询技术部的人
     * User
     */
    private void J_UserQuery() {

        //条件2：技术部
        BmobQuery<User> eq2_J = new BmobQuery<User>();
        eq2_J.addWhereEqualTo("departmentId",new BmobPointer(departments.get(0)));//departmentName ：技术部
        //组装 条件1 and 条件2：技术部
        List<BmobQuery<User>> eqList_J=new ArrayList<BmobQuery<User>>();
        eqList_J.add(eq1);
        eqList_J.add(eq2_J);

        //查询技术部的人
        BmobQuery<User> query_J = new BmobQuery<User>();
        query_J.and(eqList_J);
        query_J.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    userlist_List.add(object);
                    Snackbar.make(view, "查询技术部的人个数：" + object.size(), Snackbar.LENGTH_LONG).show();
                    M_UserQuery();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
    /**
     * 查询秘书部的人
     * User
     */
    private void M_UserQuery() {

        //条件2：秘书部
        BmobQuery<User> eq2_M = new BmobQuery<User>();
        eq2_M.addWhereEqualTo("departmentId",new BmobPointer(departments.get(1)));//departmentName ：秘书部
        //组装 条件1 and 条件2：秘书部
        List<BmobQuery<User>> eqList_M=new ArrayList<BmobQuery<User>>();
        eqList_M.add(eq1);
        eqList_M.add(eq2_M);

        //查询秘书部的人
        BmobQuery<User> query_M = new BmobQuery<User>();
        query_M.and(eqList_M);
        query_M.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    userlist_List.add(object);
                    Snackbar.make(view, "查询秘书部的人个数：" + object.size(), Snackbar.LENGTH_LONG).show();
                    W_UserQuery();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
    /**
     * 查询外联部的人
     * User
     */
    private void W_UserQuery() {

        //条件2：外联部
        BmobQuery<User> eq2_W = new BmobQuery<User>();
        eq2_W.addWhereEqualTo("departmentId",new BmobPointer(departments.get(2)));//departmentName ：外联部
        //组装 条件1 and 条件2：外联部
        List<BmobQuery<User>> eqList_W=new ArrayList<BmobQuery<User>>();
        eqList_W.add(eq1);
        eqList_W.add(eq2_W);

        //查询外联部的人
        BmobQuery<User> query_W = new BmobQuery<User>();
        query_W.and(eqList_W);
        query_W.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    userlist_List.add(object);
                    Snackbar.make(view, "查询外联部的人个数：" + object.size(), Snackbar.LENGTH_LONG).show();
                    X_UserQuery();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
    /**
     * 查询宣传部的人
     * User
     */
    private void X_UserQuery() {

        //条件2：宣传部
        BmobQuery<User> eq2_X = new BmobQuery<User>();
        eq2_X.addWhereEqualTo("departmentId",new BmobPointer(departments.get(3)));//departmentName ：宣传部
        //组装 条件1 and 条件2：宣传部
        List<BmobQuery<User>> eqList_X=new ArrayList<BmobQuery<User>>();
        eqList_X.add(eq1);
        eqList_X.add(eq2_X);

        //查询宣传部的人
        BmobQuery<User> query_X = new BmobQuery<User>();
        query_X.and(eqList_X);
        query_X.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    userlist_List.add(object);
                    Snackbar.make(view, "查询宣传部的人个数：" + object.size(), Snackbar.LENGTH_LONG).show();
                    Z_UserQuery();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
    /**
     * 查询组织部的人
     * User
     */
    private void Z_UserQuery() {

        //条件2：组织部
        BmobQuery<User> eq2_Z = new BmobQuery<User>();
        eq2_Z.addWhereEqualTo("departmentId",new BmobPointer(departments.get(4)));//departmentName ：组织部
        //组装 条件1 and 条件2：组织部
        List<BmobQuery<User>> eqList_Z=new ArrayList<BmobQuery<User>>();
        eqList_Z.add(eq1);
        eqList_Z.add(eq2_Z);

        //查询组织部的人
        BmobQuery<User> query_Z = new BmobQuery<User>();
        query_Z.and(eqList_Z);
        query_Z.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    userlist_List.add(object);
                    Snackbar.make(view, "查询组织部的人个数：" + object.size(), Snackbar.LENGTH_LONG).show();
                    MemberAdapter memberAdapter=new MemberAdapter(userlist_List);
                    c_member_rec_view.setAdapter(memberAdapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

    private void initView(View view) {
        c_member_rec_view = (RecyclerView) view.findViewById(R.id.c_member_rec_view);
    }
}
