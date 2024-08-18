package com.example.market.dao;

import com.example.market.dto.FileDto;
import com.example.market.dto.ProductDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface ProductDao {

    @Select("select * from product")
    List<ProductDto> findAll() throws DataAccessException;

    @Select("SELECT * FROM product WHERE id = #{id}")
    ProductDto findById(@Param("id") Long id) throws DataAccessException;

    @Insert("INSERT INTO product (name, description, category, price, imageUrl)" +
            " VALUES (#{name}, #{description}, #{category}, #{price}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ProductDto productDto) throws DataAccessException;

    @Update("UPDATE product SET name = #{name}, description = #{description}, " +
            "category = #{category}, price = #{price}, imageUrl = #{imageUrl} " +
            "WHERE id = #{id}")
    void update(ProductDto productDto) throws DataAccessException;

    @Select("SELECT * FROM product WHERE name LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY id DESC LIMIT #{offset}, #{recordSize}")
    List<FileDto> searchProducts(@Param("keyword") String keyword,
                                 @Param("recordSize") int recordSize,
                                 @Param("offset") int offset) throws DataAccessException;

//    int countProducts(String keyword);
}
