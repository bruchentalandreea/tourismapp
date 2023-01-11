import { Injectable } from '@angular/core';
import { FeedbackPayload } from '../user-profile/feedback-payload';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  constructor(private httpClient: HttpClient) {
  }

  addFeedback(feedbackPayload: FeedbackPayload){
    return this.httpClient.post('http://localhost:8080/api/feedback', feedbackPayload);
  }

  addFeedbackHoliday(feedbackPayload: FeedbackPayload){
    return this.httpClient.post('http://localhost:8080/api/feedback/holiday', feedbackPayload);
  }
}
