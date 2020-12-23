package com.revature.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.ReimbursementDaoImpl;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.util.ReimbTemplate;

public class ReimbursementServiceImpl implements ReimbursementService {

	private static Logger log = Logger.getLogger(ReimbursementServiceImpl.class);

	private static ReimbursementDao reimbDao = new ReimbursementDaoImpl();
	private static UserServiceImpl userServiceImpl = new UserServiceImpl();

	@Override
	public int addReimbursement(Reimbursement r) {
		log.info("Calling ReimbursementServiceImpl.addReimbursement to add a reimb to db");
		int id = 0;
		try {
			log.info("Submisison started");
			id = reimbDao.insertReimbursement(r);
			log.info("The reimb " + id + " added. Exiting ReimbursementServiceImpl.");
			return id;
		} catch (Exception e) {
			log.warn("Failed to add. Error code: ", e);
		}
		return 0;
	}

	@Override
	public List<Reimbursement> getAllReimbursements() {
		log.info("Calling ReimbursementServiceImpl.getAllReimbursements to add a reimb to db");
		List<Reimbursement> list = reimbDao.selectAllReimbursements();
		if (list != null) {
			return list;
		} else {
			log.warn("Not found.");
			return null;
		}
	}

	@Override
	public Reimbursement getReimbursementById(int id) {
		log.info("Calling ReimbursementServiceImpl.getReimbursementById to get reimb id " + id);
		Reimbursement reimb = reimbDao.selectReimbursementById(id);
		log.info("Found reimb " + reimb);
		return reimb;
	}

	@Override
	public List<Reimbursement> getReimbursementsByStatusId(int statusId) {
		log.info("Calling ReimbursementServiceImpl.getReimbursementsByStatusId to get reimb id " + statusId);
		List<Reimbursement> list = reimbDao.selectReimbursementsByStatusId(statusId);
		log.info("Reimbursement list found with status id" + statusId);
		return list;
	}

	@Override
	public List<Reimbursement> getReimbursementsByAuthorId(int authorId) {
		log.info("Calling ReimbursementServiceImpl.getReimbursementsByAuthorId to get reimb " + authorId);

		List<Reimbursement> list = reimbDao.selectReimbursementsByAuthorId(authorId);
		log.info("Found reimb " + authorId);
		return list;
	}

	@Override
	public void deleteReimbursement(Reimbursement r) {
		log.info("Calling ReimbursementServiceImpl.deleteReimbursement");
		try {
			reimbDao.deleteReimbursement(r);
		} catch (Exception e) {
			log.warn("Failed to delete: Error code", e);
		}
	}

	public Reimbursement convertToReimb(ReimbTemplate temp) {
		double rAmount = Double.parseDouble(temp.getAmount());
		LocalDateTime sDateTime = LocalDateTime.now();
		LocalDateTime rDateTime = null;
		int statusId = Integer.parseInt(Arrays.asList(temp.getReimbursementStatus()).get(0));
		String statusName = Arrays.asList(temp.getReimbursementStatus()).get(1);
		int typeId = Integer.parseInt(Arrays.asList(temp.getReimbursementType()).get(0));
		String typeName = Arrays.asList(temp.getReimbursementType()).get(1);
		User author = userServiceImpl.getUserByUserId(Integer.valueOf(temp.getAuthorId()));
		User resolver = new User();

		return new Reimbursement(BigDecimal.valueOf(rAmount), sDateTime, rDateTime, temp.getDescription(), author,
				resolver, new ReimbursementStatus(statusId, statusName), new ReimbursementType(typeId, typeName));
	}

	public ReimbursementDTO convertToDTO(Reimbursement r) {
		System.out.println(r);
		return new ReimbursementDTO(r.getrId(), r.getAmount().toPlainString(), r.getSubmissionDateTime().toString(), "",
				r.getDescription(), r.getAuthor().getUserId(), 0, r.getStatus().getStatusId(),
				r.getStatus().getStatusName(), r.getType().getTypeId(), r.getType().getTypeName());
	}

	public ReimbursementDTO convertToDTOFull(Reimbursement r) {
		System.out.println(r);
		return new ReimbursementDTO(r.getrId(), r.getAmount().toPlainString(), r.getSubmissionDateTime().toString(),
				r.getResolutionDateTime().toString(), r.getDescription(), r.getAuthor().getUserId(),
				r.getResolver().getUserId(), r.getStatus().getStatusId(), r.getStatus().getStatusName(),
				r.getType().getTypeId(), r.getType().getTypeName());
	}
}
