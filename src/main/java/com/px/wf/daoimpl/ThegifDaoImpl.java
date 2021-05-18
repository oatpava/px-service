package com.px.wf.daoimpl;

import com.px.wf.dao.ThegifDao;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.wf.entity.Thegif;
import com.px.wf.entity.ThegifDepartment;
import com.px.share.util.Common;
import com.px.wf.service.ThegifDepartmentService;
import com.px.wf.service.ThegifService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class ThegifDaoImpl extends GenericDaoImpl<Thegif, Integer> implements ThegifDao {

    private final String fieldSearchReceive;
    private final String fieldSearchStatus;

    public ThegifDaoImpl() {
        super(Thegif.class);
        fieldSearchReceive = "thegifSenderDepartmentCode,thegifReceiverDepartmentCode,thegifBookNo,thegifBookDate,thegifSubject,thegifSecret,thegifSpeed,thegifSendDate,thegifLetterStatus";
        fieldSearchStatus = "thegifElementType,thegifBookNo,thegifBookDate,thegifSubject,thegifAcceptDate,thegifAcceptId,thegifAcceptDepartmentCode,thegifLetterStatus";
    }

    @Override
    public Thegif getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Thegif> listByElementType(String elementType, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        Conjunction conjunction2 = Restrictions.conjunction();
        if (elementType != null && !elementType.equals("")) {
            conjunction.add(Restrictions.eq("thegifElementType", elementType));
        } else {
            conjunction2.add(Restrictions.eq("this.thegifElementType", "CorrespondenceLetter"));
            conjunction.add(Restrictions.not(conjunction2));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<Thegif> listByBookNo(String bookNo, String elementType, String depCode, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("thegifBookNo", bookNo));

        if (elementType.indexOf(",") > -1) {
            String[] listelementType = elementType.split(",");
            conjunction.add(Restrictions.in("this.thegifElementType", listelementType));
        } else if (elementType != null && !elementType.equals("")) {
            conjunction.add(Restrictions.eq("this.thegifElementType", elementType));
        }

        if (depCode != null && !depCode.equals("")) {
            conjunction.add(Restrictions.eq("this.thegifAcceptDepartmentCode", depCode));
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Thegif> listByElementType(String elementType) {
        Conjunction conjunction = Restrictions.conjunction();
        Conjunction conjunction2 = Restrictions.conjunction();
        if (elementType != null && !elementType.equals("")) {
            conjunction.add(Restrictions.eq("thegifElementType", elementType));
        } else {
            conjunction2.add(Restrictions.eq("this.thegifElementType", "CorrespondenceLetter"));
            conjunction.add(Restrictions.not(conjunction2));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<Thegif> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAllListByElementType(String elementType) {
        return this.listByElementType(elementType).size();
    }

    @Override
    public List<Thegif> searchThegifReceive(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearchThegifReceive(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);

        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public int countSearchThegifReceive(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = createConjunctionFormSearchThegifReceive(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);

        return this.listByCriteria(criteria).size();
    }

    @Override
    public Conjunction createConjunctionFormSearchThegifReceive(MultivaluedMap<String, String> queryParams) {
        ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
        ThegifService thegifService = new ThegifService();
        Conjunction conjunction = Restrictions.conjunction();

        for (String key : queryParams.keySet()) {
            if (fieldSearchReceive.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "thegifSenderDepartmentCode":
                                //get code thegifSenderDepartmentName
                                List<String> listThegifSender = new ArrayList<>();
                                if (!valueArray[i].equals("")) {
                                    List<ThegifDepartment> listThegifDepartment1 = thegifDepartmentService.listByThegifDepartmentName(valueArray[i]);
                                    if (!listThegifDepartment1.isEmpty()) {
                                        for (ThegifDepartment thegifDepartment : listThegifDepartment1) {
                                            listThegifSender.add(thegifDepartment.getThegifDepartmentCode());
                                        }
                                    } else {
                                        listThegifSender.add("-99999");
                                    }
                                }
                                conjunction.add(Restrictions.in("this.thegifSenderDepartmentCode", listThegifSender));
                                break;
                            case "thegifReceiverDepartmentCode":
                                //get code thegifReceiverDepartmentName
                                List<String> listThegifReceiver = new ArrayList<>();
                                if (!valueArray[i].equals("")) {
                                    List<ThegifDepartment> listThegifDepartment1 = thegifDepartmentService.listByThegifDepartmentName(valueArray[i]);
                                    if (!listThegifDepartment1.isEmpty()) {
                                        for (ThegifDepartment thegifDepartment : listThegifDepartment1) {
                                            listThegifReceiver.add(thegifDepartment.getThegifDepartmentCode());
                                        }
                                    } else {
                                        listThegifReceiver.add("-99999");
                                    }
                                }
                                conjunction.add(Restrictions.in("this.thegifReceiverDepartmentCode", listThegifReceiver));
                                break;
                            case "thegifBookNo":
                                conjunction.add(Restrictions.like("this.thegifBookNo", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "thegifBookDate":
                                Date thegifBookDate = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(thegifBookDate);
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                String strBookDate = format1.format(calendar.getTime());
                                conjunction.add(Restrictions.like("this.thegifBookDate", strBookDate, MatchMode.ANYWHERE));
                                break;
                            case "thegifSubject":
                                conjunction.add(Restrictions.like("this.thegifSubject", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "thegifSecret":
                                conjunction.add(Restrictions.like("this.thegifSecret", valueArray[i], MatchMode.END));
                                break;
                            case "thegifSpeed":
                                conjunction.add(Restrictions.like("this.thegifSpeed", valueArray[i], MatchMode.END));
                                break;
                            case "thegifLetterStatus":
                                String letterStatus = thegifService.textToLetterStatus(valueArray[i]);
                                conjunction.add(Restrictions.eq("this.thegifLetterStatus", letterStatus));
                                break;
                            case "thegifSendDate":
                                Date thegifSendDate = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(thegifSendDate);
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                String strSendDate = format2.format(calendar2.getTime());
                                conjunction.add(Restrictions.like("this.thegifSendDate", strSendDate, MatchMode.ANYWHERE));
                                break;
                        }
                    }
                }
            }
        }
        conjunction.add(Restrictions.eq("this.thegifElementType", "CorrespondenceLetter"));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        return conjunction;
    }

    @Override
    public List<Thegif> searchThegifStatus(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearchThegifStatus(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);

        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public int countSearchThegifStatus(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = createConjunctionFormSearchThegifStatus(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(Thegif.class);
        criteria.add(conjunction);

        return this.listByCriteria(criteria).size();
    }

    @Override
    public Conjunction createConjunctionFormSearchThegifStatus(MultivaluedMap<String, String> queryParams) {
        ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
        ThegifService thegifService = new ThegifService();
        Conjunction conjunction = Restrictions.conjunction();
        Conjunction conjunction2 = Restrictions.conjunction();

        for (String key : queryParams.keySet()) {
            if (fieldSearchStatus.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "thegifElementType":
                                conjunction.add(Restrictions.eq("this.thegifElementType", valueArray[i]));
                                break;
                            case "thegifBookNo":
                                conjunction.add(Restrictions.like("this.thegifBookNo", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "thegifBookDate":
                                Date thegifBookDate = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(thegifBookDate);
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                String strBookDate = format1.format(calendar.getTime());
                                conjunction.add(Restrictions.like("this.thegifBookDate", strBookDate, MatchMode.ANYWHERE));
                                break;
                            case "thegifSubject":
                                conjunction.add(Restrictions.like("this.thegifSubject", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "thegifAcceptId":
                                conjunction.add(Restrictions.like("this.thegifAcceptId", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "thegifAcceptDepartmentCode":
                                //get code thegifAcceptDepartmentCode
                                List<String> listThegifAccept = new ArrayList<>();
                                if (!valueArray[i].equals("")) {
                                    List<ThegifDepartment> listThegifDepartment1 = thegifDepartmentService.listByThegifDepartmentName(valueArray[i]);
                                    if (!listThegifDepartment1.isEmpty()) {
                                        for (ThegifDepartment thegifDepartment : listThegifDepartment1) {
                                            listThegifAccept.add(thegifDepartment.getThegifDepartmentCode());
                                        }
                                    } else {
                                        listThegifAccept.add("-99999");
                                    }
                                }
                                conjunction.add(Restrictions.in("this.thegifAcceptDepartmentCode", listThegifAccept));
                                break;
                            case "thegifLetterStatus":
                                String letterStatus = thegifService.textToLetterStatus(valueArray[i]);
                                conjunction.add(Restrictions.eq("this.thegifLetterStatus", letterStatus));
                                break;
                            case "thegifAcceptDate":
                                Date thegifAcceptDate = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(thegifAcceptDate);
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                String strAcceptDate = format2.format(calendar2.getTime());
                                conjunction.add(Restrictions.like("this.thegifAcceptDate", strAcceptDate, MatchMode.ANYWHERE));
                                break;
                        }
                    }
                }
            }
        }
        conjunction2.add(Restrictions.eq("this.thegifElementType", "CorrespondenceLetter"));
        conjunction.add(Restrictions.not(conjunction2));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        return conjunction;
    }

}
