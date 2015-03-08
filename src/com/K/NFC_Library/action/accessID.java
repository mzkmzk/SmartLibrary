package com.K.NFC_Library.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.bean.temporary_values;
import com.K.NFC_Library.bean.user;
import com.K.NFC_Library.dao.Book_Dao;
import com.K.NFC_Library.dao.User_Dao;
import com.K.NFC_Library.utils.Utils;
import com.opensymphony.xwork2.Action;

/**
 * 此类 用作处理 NFC板子传过来的 id
 * @author maizhikun
 *
 */
public class accessID implements Action{
	private int id;
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		User_Dao ud =new User_Dao();
		Book_Dao bd =new Book_Dao();
		String NFC_ID =  request.getParameter("NFC_ID");
		if(!NFC_ID.equals("0")){
			System.out.println("---------------------------接受到的识别ID 是 "+NFC_ID);
			temporary_values.NFCID_NOW=NFC_ID;//把最新的NFCID值存在temporary_values 给添加用户/卡得功能
		}
		//流程 当用户还没通过ID验证时 判断ID属性用户NFC卡/还书卡
		if(temporary_values.U != null){
			System.out.println("验证 ID属于用户卡");
			//当用户不为空时 验证 ID属于用户卡
			if(NFC_ID .equals(temporary_values.U.getU_NO())){
				System.out.println("双重验证id成功 向数据库执行借书操作");
				//向数据库执行借书操作
				bd.accessBook(Utils.getSystemTime());
				temporary_values.CheckAccessSuccess=1;
				temporary_values.U=null;
				temporary_values.LB=new ArrayList<book>();
			}else{
				System.out.println("借书 开始 验证当前集合是否有重复的书籍 ");
				//记录此书ID是否有重复
				int check_repetition =0;
				//查询当前借书集合中有无重复书籍
				for(int i =0;i<temporary_values.LB.size();i++){
					if(temporary_values.LB.get(i).getB_NO().equals(NFC_ID)){
						check_repetition=1;
						System.out.println("借书 失败 出现 重复 ");
					}
				}
				
				//当此书id无重复时 向集合中添加需借书籍
				if(check_repetition==0){
					System.out.println("借书 成功 当前集合无重复书籍 ");
					book b = bd.queryBook(NFC_ID);
					if(b!=null){
						temporary_values.LB.add(b);
					}
				}
				
			}
		}else{
			System.out.println("判断 是用户卡 还是还书");
			//判断 用户卡还是 借书
			temporary_values.U = ud.NFC_LoginUser(NFC_ID);
			//当查无此用户时 判断是否为还书
			if(temporary_values.U == null){
				System.out.println("非用户卡 开始还书");
				//记录此书ID是否有重复book_return
				int check_repetition_return =0;
				//查询当前借书集合中有无重复书籍
				for(int i =0;i<temporary_values.LU_return.size();i++){
					if(temporary_values.LU_return.get(i).getBook_return().getB_NO().equals(NFC_ID)){
						check_repetition_return=1;
						System.out.println("还书 失败 出现 重复 ");
					}
				}
				//当此书id无重复时 向集合中添加需换书籍
				if(check_repetition_return==0){
					System.out.println("还书 成功 当前集合无重复书籍 ");
					user u_return  = bd.queryBook_return(NFC_ID,Utils.getSystemTime());
					if(u_return !=null){
						System.out.println(u_return);
						temporary_values.LU_return.add(u_return);
					}
				}
			}
		}
		return SUCCESS;
	}
	

}
