package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有标签
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 删除标签
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 插入标签
     */
    public void addLable(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 更新标签
     */
    public void updateLabel(Label label) {
        labelDao.save(label);
    }

    /**
     * 根据条件查询标签
     */
    public List<Label> findSearch(Label label) {

        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象，也就是把哪个对象封装到条件中 where label.id = id
             * @param criteriaQuery  封装的都是查询关键字 例如 group by order by
             * @param cb 用来封装条件对象的
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                ArrayList list = new ArrayList();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//where labelname like "小明"
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());//where labelname like "小明"
                    list.add(predicate);
                }
                Predicate[] pre = new Predicate[list.size()];
                list.toArray(pre);
                return cb.and(pre);
            }
        });
    }

    /**
     * 含有分页条件的查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> queryPage(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                ArrayList list = new ArrayList();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//where labelname like "小明"
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());//where labelname like "小明"
                    list.add(predicate);
                }
                Predicate[] pre = new Predicate[list.size()];
                list.toArray(pre);
                return cb.and(pre);
            }
        },pageable);
    }
}
