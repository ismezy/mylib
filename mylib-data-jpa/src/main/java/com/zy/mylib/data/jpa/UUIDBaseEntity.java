package com.zy.mylib.data.jpa;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 扬
 * @date 2017/3/23
 */
@MappedSuperclass
public abstract class UUIDBaseEntity extends JpaEntity {
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Id
    @Column(length = 32)
    @JsonView(BaseView.class)
    protected String id;

    public static <T extends UUIDBaseEntity> T newEntity(String id, Class<T> type) {
        try {
            T ret = type.newInstance();
            ret.setId(id);
            return ret;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("error");
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public String getId() {
        return id;
    }
}
