package com.want.common.audit;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

  @NotNull
  @Override
  public Optional<Long> getCurrentAuditor() {
    return Optional.empty();
  }
}
