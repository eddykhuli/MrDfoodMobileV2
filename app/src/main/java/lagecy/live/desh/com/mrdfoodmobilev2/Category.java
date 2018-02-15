package lagecy.live.desh.com.mrdfoodmobilev2;

import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/01/11.
 */

class Category {

    private Long id;
    private Long restId;
    private String categoryName;
    private List<Product> product;

    public Category(){}

    public Category(Long id, Long restId, String categoryName, List<Product> product) {
        this.id = id;
        this.restId = restId;
        this.categoryName = categoryName;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
