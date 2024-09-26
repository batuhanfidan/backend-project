package com.example.demo;

import com.example.demo.controller.CategoriesController;
import com.example.demo.controller.ProductsController;
import com.example.demo.controller.UserController;
import com.example.demo.dto.UsersResponse;
import com.example.demo.entity.Categorie;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.entity.Users;
import com.example.demo.service.CategoriesService;
import com.example.demo.service.ProductsService;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTests {

    @Mock
    private CategoriesService categoriesService;
    @Mock
    private ProductsService productsService;
    @Mock
    private UsersService usersService;



    @InjectMocks
    private CategoriesController categoriesController;
    @InjectMocks
    private ProductsController productsController;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCategoriesController() {

        List<Categories> expectedCategories = new ArrayList<>();

        Categories category1 = new Categories();
        category1.setId(1L);
        category1.setName(Categorie.SMART);
        expectedCategories.add(category1);

        Categories category2 = new Categories();
        category2.setId(2L);
        category2.setName(Categorie.ELECTRONICS);
        expectedCategories.add(category2);

        when(categoriesService.findAll()).thenReturn(expectedCategories);

        List<Categories> actualCategories = categoriesController.getCategories();

        assertEquals(expectedCategories, actualCategories);
        verify(categoriesService, times(1)).findAll();
    }

    @Test
    void testProductsController() {

        Long productId = 1L;
        Products expectedProduct = new Products();
        expectedProduct.setId(productId);
        expectedProduct.setName("TEST");
        expectedProduct.setPrice(100);
        expectedProduct.setStock(10);

        Categories category = new Categories();
        category.setId(1L);
        category.setName(Categorie.ELECTRONICS);
        when(productsService.findById(productId)).thenReturn(expectedProduct);
        ResponseEntity<Products> response = productsController.getProductById(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProduct, response.getBody());
        verify(productsService, times(1)).findById(productId);
    }


    @Test
    void testUserController() {

        Users newUser = new Users();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password");
        newUser.setFullName("New User");
        newUser.setPhoneNumber("1234567890");
        newUser.setAdress("123 Test St");
        newUser.setUserName("newuser");


        newUser.setRoles(new ArrayList<>());
        newUser.setFavoriteProducts(new ArrayList<>());
        newUser.setBasket(new ArrayList<>());


        UsersResponse savedUserResponse = new UsersResponse(
                1L,
                "newuser",
                "newuser@example.com",
                "New User",
                "1234567890",
                "123 Test St",
                new ArrayList<>(),
                new ArrayList<>()
        );

        when(usersService.save(any(Users.class))).thenReturn(savedUserResponse);

        ResponseEntity<UsersResponse> response = userController.createUser(newUser);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedUserResponse, response.getBody());
        verify(usersService, times(1)).save(newUser);
    }



}