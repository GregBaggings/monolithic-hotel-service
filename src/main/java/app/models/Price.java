package app.models;

import javax.persistence.*;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
@Entity
@Table(name = "prices")
public class Price {

    @Column(name = "hotel_id", nullable = false)
    private int hotel_id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id", nullable = false)
    private int room_id;
    @Column(name = "room_name", nullable = false)
    private String room_name;
    @Column(name = "price", nullable = false)
    private int price;

    public int getRoomId() {
        return room_id;
    }

    public void setRoomId(int id) {
        this.room_id = room_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
