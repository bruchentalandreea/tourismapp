import { Component, OnInit, ViewEncapsulation, Input  } from '@angular/core';
import { Router } from '@angular/router';
import { PostPayload } from '../add-post/post-payload';
import { AddPostService } from '../service/add-post.service';

@Component({
  selector: 'app-display-post',
  templateUrl: './display-post.component.html',
  styleUrls: ['./display-post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DisplayPostComponent implements OnInit {

  @Input() posts: PostPayload[];

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

}
