package com.change.mvpdemo.modle;

/**
 * MVPģʽ��m��ģ�ͣ��㡣
 * ��½״̬����½��ʵ���߼�ʵ����ȥ��ɡ�
 * @author Change
 *
 */
public interface ILoginStatus extends IStatus{
	public static final int STATUS_VERIFY_FAIL = -1;//��֤ʧ��
	public static final int STATUS_LOGIN_FAIL = -2;//��½ʧ��
	public static final int STATUS_LOGIN_SUCCESS = 0;//��½�ɹ�
	public static final int STATUS_LOGIN_ING = 1;//��½��
	/**
	 * ��½��Ϊ
	 * @param account
	 * @param psw
	 * @return ״̬��
	 */
	public void login(String account,String psw,IStatusCallback callback);
}
