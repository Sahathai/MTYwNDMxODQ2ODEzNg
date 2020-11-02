import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CandidatetestComponent } from './candidatetest/candidatetest.component';

const routes: Routes = [
  {path: 'candidatetest', component: CandidatetestComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
