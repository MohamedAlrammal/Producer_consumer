import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimulationService } from './services/simulation-service';
import { SimulationStateModel } from './models/simulation-state/simulation-state-module';
import { ControlPanelComponent } from './components/control-panel/control-panel';
import { QueueComponent } from './components/queue/queue';
import { MachineComponent } from './components/machine/machine';
import { DragDropModule } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    ControlPanelComponent,
    QueueComponent,
    MachineComponent,
    DragDropModule
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit {

  simulationState?: SimulationStateModel;

  constructor(private simulationService: SimulationService) { }

  ngOnInit(): void {
    this.simulationService.simulationState$
      .subscribe(state => this.simulationState = state);
  }
}
