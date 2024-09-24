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

    @Mock
    private UsersResponse usersResponse;

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
        // Arrange
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

        // Act
        List<Categories> actualCategories = categoriesController.getCategories();

        // Assert
        assertEquals(expectedCategories, actualCategories);
        verify(categoriesService, times(1)).findAll();
    }

    @Test
    void testProductsController() {
        // Arrange
        Long productId = 1L;
        Products expectedProduct = new Products();
        expectedProduct.setId(productId);
        expectedProduct.setName("TEST");
        // Assuming Products has a 'quantity' field
        expectedProduct.setQuantity(10);
        // Assuming Products has a 'categoryId' field
        expectedProduct.setCategoryId(1L);

        when(productsService.findById(productId)).thenReturn(expectedProduct);

        // Act
        ResponseEntity<Products> response = productsController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProduct, response.getBody());
        verify(productsService, times(1)).findById(productId);
    }

    @Test
    void testUserController() {
        // Arrange
        Users newUser = new Users();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password");
        newUser.setFullName("New User");
        newUser.setPhoneNumber("1234567890");
        newUser.setAdress("123 Test St");
        newUser.setUserName("newuser");

        // Optionally set empty lists for roles, favoriteProducts, and basket
        newUser.setRoles(new ArrayList<>());
        newUser.setFavoriteProducts(new ArrayList<>());
        newUser.setBasket(new ArrayList<>());

        UsersResponse savedUserResponse = usersResponse;
        savedUserResponse.setId(1L);
        savedUserResponse.setEmail("newuser@example.com");
        savedUserResponse.setPassword("password");
        savedUserResponse.setFullName("New User");
        savedUserResponse.setPhoneNumber("1234567890");
        savedUserResponse.setAdress("123 Test St");
        savedUserResponse.setUserName("newuser");

        when(usersService.save(any(Users.class))).thenReturn(savedUserResponse);

        // Act
        ResponseEntity<UsersResponse> response = userController.createUser(newUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedUserResponse, response.getBody());
        verify(usersService, times(1)).save(newUser);
    }

}