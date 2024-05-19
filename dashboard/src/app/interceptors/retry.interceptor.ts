import { HttpInterceptorFn } from '@angular/common/http';
import { RetryConfig, retry } from 'rxjs';

const defaultRetryConfig: RetryConfig={
  count: 4,
  delay: 3000
}

export const retryInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(retry(defaultRetryConfig));
};
