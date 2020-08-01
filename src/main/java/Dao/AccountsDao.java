package Dao;

import Model.AccountsEntity;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AccountsDao {
    private static Session accountSession;
    public static final String ERROR_INVALID="Tên tài khoản hoac mat khau khong hop le!";
    public static final String ERROR_DUPLICATE="Tài khoản đã tồn tại!";
    public static final String ERROR="Đã có lỗi xảy ra, xin vui lòng thử lại!";
    public static final String SUCCESS_CREATED="Tạo tài khoản thành công!";
    public static final String ERROR_OLDPASS="Mat khau khong hop le!";
    public static final String SUCCESS_CHANGED_PASS="Đổi mật khẩu thành công!";



    public AccountsDao() {

    }

    private static String hashMD5Password(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();

        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    public String createAccount(String username, String password) {
        if(username.length() == 0 || username.length() == 0) {
            return ERROR_INVALID;
        }


        Transaction transaction = null;
        try {
            accountSession = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM AccountsEntity a Where a.username= '" + username + "'";
            Query query = accountSession.createQuery(hql);

            if(query.getResultList().size() > 0) {
                System.out.println("Tai khoan da ton tai!");
                return ERROR_DUPLICATE;
            }

            //create account instance
            AccountsEntity accounts = new AccountsEntity();
            accounts.setUsername(username);
            accounts.setPassword(password);
            accounts.setPassword(hashMD5Password(accounts.getPassword()));
            transaction = accountSession.beginTransaction();

            accountSession.save(accounts);

            transaction.commit();
            System.out.println("Created Account " + username);
            accountSession.close();
            return SUCCESS_CREATED;
        }catch (Exception e) {
            e.printStackTrace();
        }
        accountSession.close();
        return ERROR;
    }

    public AccountsEntity login(String username, String password) throws NoSuchAlgorithmException {
        try{
            accountSession = HibernateUtil.getSessionFactory().openSession();
            String hashPass = hashMD5Password(password);

            String hql = "SELECT a from AccountsEntity a WHERE a.password='"+ hashPass +"' and a.username='"+ username +"'";
            Query query = accountSession.createQuery(hql);
            if(query.getResultList().size() == 0) {
                accountSession.close();
                return null;
            }
            AccountsEntity user = (AccountsEntity) query.getResultList().get(0);

            accountSession.close();
            return user;

        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Login + da co loi!");
        accountSession.close();
        return null;
    }

    public String changePassword(String username, String oldPassword, String newPassword) {
        try {
            accountSession = HibernateUtil.getSessionFactory().openSession();

            String hashOldPass = hashMD5Password(oldPassword);
            String hql = "SELECT a from AccountsEntity a WHERE a.password='"+ hashOldPass +"' and a.username='"+ username +"'";
            Query query = accountSession.createQuery(hql);
            List<AccountsEntity> listAccounts = query.getResultList();

            if(listAccounts.size() == 0) {
                accountSession.close();
                return ERROR_OLDPASS;
            }
            AccountsEntity a = listAccounts.get(0);

            Transaction transaction = null;
            String hashNewPass = hashMD5Password(newPassword);
            a.setPassword(hashNewPass);
            transaction = accountSession.beginTransaction();
            accountSession.update(a);
            transaction.commit();
            accountSession.close();
            return SUCCESS_CHANGED_PASS;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            accountSession.close();
        }
        return ERROR;
    }

    public static AccountsEntity getAccount(int userID) {
        try {
            accountSession = HibernateUtil.getSessionFactory().openSession();
            String hql = "select a from AccountsEntity a where a.accountId="+ userID +"";
            Query query = accountSession.createQuery(hql);
            List<AccountsEntity> result = query.getResultList();
            if(result.size() == 0) {
                return null;
            }
            accountSession.close();
            return result.get(0);
        }catch (Exception e) {
            System.out.println("Error get account");
            e.printStackTrace();
            accountSession.close();
        }
        return null;
    }

    public static AccountsEntity getAccountByUsername(String username) {
        try {
            accountSession = HibernateUtil.getSessionFactory().openSession();
            String hql = "select a from AccountsEntity a where a.username='"+ username +"'";
            Query query = accountSession.createQuery(hql);
            List<AccountsEntity> result = query.getResultList();
            if(result.size() == 0) {
                return null;
            }
            accountSession.close();
            return result.get(0);
        }catch (Exception e) {
            System.out.println("Error get account");
            e.printStackTrace();
            accountSession.close();
        }
        return null;
    }
}
