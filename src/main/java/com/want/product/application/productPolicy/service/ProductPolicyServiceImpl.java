package com.want.product.application.productPolicy.service;

import com.want.product.domain.repository.ProductPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductPolicyServiceImpl implements ProductPolicyService {

  private final ProductPolicyRepository productPolicyRepository;

}
