package com.example.ph36187_pnlib.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ph36187_pnlib.DAO.ThuThuDAO;
import com.example.ph36187_pnlib.Model.ThuThu;
import com.example.ph36187_pnlib.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassFragment extends Fragment {
    TextInputEditText edPassOld,edPass,edRePass;
    Button btnSave,btnCancel;
    ThuThuDAO dao;
    public ChangePassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        edPassOld = view.findViewById(R.id.edPassOld);
        edPass = view.findViewById(R.id.edPassChange);
        edRePass = view.findViewById(R.id.edRePassChange);
        btnSave = view.findViewById(R.id.btnSaveUserChange);
        dao = new ThuThuDAO(getActivity());
        btnCancel = view.findViewById(R.id.btnCancelUserChange);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPass.setText("");
                edPassOld.setText("");
                edRePass.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");
                if(validate()>0){
                    ThuThu thuThu = dao.getid(user);
                    thuThu.setMatKhau(edPass.getText().toString());
                    dao.updatePass(thuThu);
                    if(dao.updatePass(thuThu)>0){
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                    }else {
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        return view;

    }
    public int validate(){
        int check = 1;
        if(edPassOld.getText().length()==0||edPass.getText().length()==0||edRePass.getText().length()==0){
            Toast.makeText(getContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            //Đọc user, pas tỏng sharedPreferences
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD","");
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if(!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(),"Mật khẩu cũ sai",Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if(pass.equals(edRePass)){
                Toast.makeText(getContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}