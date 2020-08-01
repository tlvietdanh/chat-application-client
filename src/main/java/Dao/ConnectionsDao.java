package Dao;

import Model.AccountsEntity;
import Model.ConnectionsEntity;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsDao {
    private static Session connectionSession;
    public static final String ERROR_INVALID="Liên kết không ton tại!";
    public static final String ERROR_DUPLICATE="Tài khoản đã tồn tại!";
    public static final String ERROR="Đã có lỗi xảy ra, xin vui lòng thử lại!";
    public static final String SUCCESS_CREATED="Tạo kết nối thành công!";
    public static final String ERROR_OLDPASS="Mat khau khong hop le!";
    public static final String SUCCESS_CHANGED_PASS="Đổi mật khẩu thành công!";
    public static final String ACCEPTED = "ACCEPTED";
    public static final String WAITING = "WAITING";
    public static final String CANCELLED = "CANCELLED";

    public String createConnection(int requestID, int receiveID, String timeStamp) {
        try {
            connectionSession = HibernateUtil.getSessionFactory().openSession();

            // check is connection existed
            String hql = "select c from ConnectionsEntity c where c.requestId=" + requestID + " and c.receiveId=" + receiveID + "";
            Query query = connectionSession.createQuery(hql);
            List result = query.getResultList();

            ConnectionsEntity connectionsEntity = new ConnectionsEntity();
            if(result.size() > 0) {
                connectionsEntity = (ConnectionsEntity) result.get(0);
            }

            Transaction transaction = null;


            connectionsEntity.setRequestId(requestID);
            connectionsEntity.setReceiveId(receiveID);
            connectionsEntity.setTimestamp(timeStamp);
            connectionsEntity.setStatus(WAITING);

            transaction = connectionSession.beginTransaction();
            connectionSession.save(connectionsEntity);
            transaction.commit();

            connectionSession.close();
            return SUCCESS_CREATED;

        }catch (Exception e) {
            System.out.println("Error create connection!");
            e.printStackTrace();
            connectionSession.close();
        }
        return ERROR;
    }

    public String setStatus(int connectionID, String status, String timeStamp) {
        try {
            connectionSession = HibernateUtil.getSessionFactory().openSession();

            // check is connection existed
            String hql = "select c from ConnectionsEntity c where c.connectionId=" + connectionID + "";
            Query query = connectionSession.createQuery(hql);
            List result = query.getResultList();

            ConnectionsEntity connectionsEntity = new ConnectionsEntity();
            if(result.size() > 0) {
                connectionsEntity = (ConnectionsEntity) result.get(0);
            }
            else {
                return ERROR_INVALID;
            }

            Transaction transaction = null;


            connectionsEntity.setTimestamp(timeStamp);
            connectionsEntity.setStatus(status);

            transaction = connectionSession.beginTransaction();
            connectionSession.update(connectionsEntity);
            transaction.commit();

            connectionSession.close();
            return SUCCESS_CREATED;


        } catch (Exception e) {

        }

        return ERROR;
    }

    public List<String> getListFriend(int userID) {
        try {
            connectionSession = HibernateUtil.getSessionFactory().openSession();

            String hql = "select c from ConnectionsEntity c where c.receiveId='"+ userID +"' or c.requestId='"+ userID +"' and c.status='ACCEPTED'";
            Query query = connectionSession.createQuery(hql);
            List<ConnectionsEntity> result = query.getResultList();
            List<String> friends = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                ConnectionsEntity connectionsEntity = result.get(i);
                if(connectionsEntity.getReceiveId() == userID) {
                    AccountsEntity a = AccountsDao.getAccount(connectionsEntity.getRequestId());
                    friends.add(a.getUsername());
                }
                else {
                    AccountsEntity a = AccountsDao.getAccount(connectionsEntity.getReceiveId());
                    friends.add(a.getUsername());
                }
            }

            connectionSession.close();
            return friends;

        }catch (Exception e) {
            System.out.println("Error get list connection!");
            e.printStackTrace();
            connectionSession.close();
        }
        return null;
    }

}
