package com.epam.entity;

public class Entity {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
