package lagecy.live.desh.com.mrdfoodmobilev2;

import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/01/11.
 */

public class Restaurant {

    private Long id;
    private String restaurantName;
    private long accountId;
    private List<Category> categories ;
    private String image;
    private String address;

    public Restaurant()
    {}

    public Restaurant(Long id,String restaurantName,long accountId,List<Category> categories,String image,String address)
    {
        this.id = id;
        this.restaurantName = restaurantName;
        this.accountId = accountId;
        this.categories = categories;
        this.image = image;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
