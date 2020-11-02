import { Component } from '@angular/core';
import { Observable, forkJoin } from 'rxjs';
import { ajax } from 'rxjs/ajax';

@Component({
	selector: 'app-candidatetest',
	templateUrl: './candidatetest.component.html',
	styleUrls: ['./candidatetest.component.css']
})
export class CandidatetestComponent {
	reqIteration : number;

	callAPI () {
		var requests = [];
		for (var i = 0; i < this.reqIteration; i++)
			requests.push(ajax.get('http://localhost:8080/transformKeyValue'));
		forkJoin(requests).subscribe();
	}
}
