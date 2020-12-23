package com.revature.dao;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDao {

		public int insertReimbursement(Reimbursement reimb);

		public List<Reimbursement> selectAllReimbursements();
		public Reimbursement selectReimbursementById(int id);
		public List<Reimbursement> selectReimbursementsByStatusId(int sId);
		public List<Reimbursement> selectReimbursementsByAuthorId(int authorId);
		public List<Reimbursement> selectReimbursementsByAuthorId_NotPending(int authorId);
		public List<Reimbursement> selectReimbursementsByAuthorId_Pending(int authorId);
		
		public void updateReimbursement(Reimbursement reimb);
		public void deleteReimbursement(Reimbursement reimb);
}
