package com.diyanamancheva.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<OrderDto> printOrderById(@PathVariable int id){
    Order order = orderService.getOrderById(id);
    OrderDto orderDto = new OrderDto(order.getId(), order.getClientId(), order.getBookId(), order.getIssueDate());

    return ResponseEntity.ok(orderDto);
  }

  @GetMapping("/orders")
  public ResponseEntity<List<OrderDto>> printAllOrders() {
    List<OrderDto> orders = orderService.getAllOrders();

    return ResponseEntity.ok(orders);
  }

  @PostMapping("/orders")
  public ResponseEntity<Void> createOrder(@RequestBody OrderRequest orderRequest) {
    orderService.addOrder(orderRequest.getClientId(), orderRequest.getBookId(), orderRequest.getIssueDate());
    return ResponseEntity.status(201).build();
  }

  @PutMapping("/orders/{id}")
  public ResponseEntity<OrderDto> updateOrder(
    @RequestBody OrderRequest orderRequest, @PathVariable int id,
    @RequestParam(required = false) boolean returnOld){

    OrderDto orderDto = orderService.editOrder(id, orderRequest);

    if (returnOld){
      return ResponseEntity.ok(orderDto);
    }else{
      return ResponseEntity.noContent().build();
    }
  }
}