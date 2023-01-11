import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, finalize, retry} from 'rxjs/operators';

/*
@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // Add Auth Token
    // In production you would get the token value from an auth service
    const hardcodedToken = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MjIifQ.RyVaxWQfLjwmSKT3qYemqASj6EVLwqk6hXGngmnc2wFYZkS_Q3fnx-GpTuFhuVpthEE8cLhgg2A5Ncei4fKjoQ';
    req = req.clone({
      setHeaders: {
      
        Authorization: `Bearer ${hardcodedToken}`
      }
    });

    return next.handle(req)
      .pipe(
        // Retry on failure
        retry(2),

        // Handle errors
        catchError((error: HttpErrorResponse) => {
          // TODO: Add error handling logic here
          alert(`HTTP Error: ${req.url}`);
          return throwError(error);
        }),

        // PROFILING
        finalize(() => {
          const profilingMsg = `${req.method} "${req.urlWithParams}"`;
          console.log(profilingMsg);
        })
        );
  }
}*/