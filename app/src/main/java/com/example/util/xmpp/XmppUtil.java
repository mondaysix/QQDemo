package com.example.util.xmpp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.example.entity.UserEntity;
import com.oy.util.SharedUtil;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.packet.VCard;

import java.io.ByteArrayOutputStream;

/**
 * Created by Ken on 2016/10/17.16:12
 * 操作Openfire的工具类
 */
public class XmppUtil {

    /**
     * 注册用户
     *
     * @return 0 - 服务器没有响应
     * 1 - 注册成功
     * 2 - 账户已经存在
     * 3 - 其他的一些错误
     * <p/>
     * 该方法可以在主线程中调用
     */
    public static int registerUser(String username, String password) {
        XMPPConnection xmppConnection = XmppConnectionUtil.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isConnected()) {
            Registration reg = new Registration();
            reg.setType(IQ.Type.SET);
            reg.setTo(xmppConnection.getServiceName());
            reg.setUsername(username);
            reg.setPassword(password);

            PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()), new PacketTypeFilter(IQ.class));
            PacketCollector collector = xmppConnection.createPacketCollector(filter);
            xmppConnection.sendPacket(reg);
            IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());

            // Stop queuing results停止请求results（是否成功的结果）
            collector.cancel();
            if (result == null) {
                return 0;
            } else if (result.getType() == IQ.Type.RESULT) {
                return 1;
            } else {
                if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
                    return 2;
                } else {
                    return 3;
                }
            }

        }
        return 3;
    }

    /**
     * 登录用户名
     *
     * @return
     */
    public static boolean login(String username, String password) {
        XMPPConnection xmppConnection = XmppConnectionUtil.getXmppConnection();
        if (xmppConnection != null && xmppConnection.isConnected()) {
            if (xmppConnection.isAuthenticated()) {
                //已经登录
                return true;
            }

            //登录
            try {
                xmppConnection.login(username, password);
                if(xmppConnection.isAuthenticated()){
                    // 设置登录状态：在线
                    Presence presence = new Presence(Presence.Type.available);
                    xmppConnection.sendPacket(presence);
                    return true;
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获得当前用户的基本信息 - 昵称、头像、个性签名
     */
    public static UserEntity getUserInfo(){
        XMPPConnection xmppConnection = XmppConnectionUtil.getXmppConnection();
        if(xmppConnection != null && xmppConnection.isAuthenticated()){
            VCard vCard = new VCard();
            //读取当前账户的VCard数据
            try {
                vCard.load(xmppConnection);
                String nickName = vCard.getNickName();//获得昵称
                if(nickName == null){
                    //注册后第一次登录，则跳转到基本信息设置页面
                    return null;
                } else {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setNickName(nickName);
                    userEntity.setSignature(vCard.getField(Constants.SIGNATURE));
                    userEntity.setUserHeader(vCard.getField(Constants.HEADER));

                    return userEntity;
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存基本信息
     * @param userEntity
     * @return
     */
    public static boolean saveUserInfo(UserEntity userEntity){
        XMPPConnection xmppConnection = XmppConnectionUtil.getXmppConnection();
        VCard vCard = new VCard();
        if(xmppConnection != null && xmppConnection.isAuthenticated()){

            try {

                vCard.setNickName(userEntity.getNickName());
                vCard.setField(Constants.SIGNATURE,userEntity.getSignature());

                vCard.setField(Constants.HEADER,userEntity.getUserHeader());
                //保存基本信息
                vCard.save(xmppConnection);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将Bitmap装换成byte[]数组
     * @return
     */
    private static byte[] bitmap2Bytes(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }
}
