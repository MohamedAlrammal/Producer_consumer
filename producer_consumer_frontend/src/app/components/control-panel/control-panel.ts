import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimulationService } from '../../services/simulation-service';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './control-panel.html',
  styleUrls: ['./control-panel.css']
})
export class ControlPanelComponent {

  isRunning = false;

  constructor(private simulationService: SimulationService) { }

  // =====================
  // Add buttons
  // =====================

  addMachine() {
    this.simulationService.addMachine();
  }

  addQueue() {
    this.simulationService.addQueue();
  }

  // =====================
  // Simulation controls
  // =====================

  startSimulation() {
    this.simulationService.start();
    this.isRunning = true;
  }

  stopSimulation() {
    this.simulationService.stop();
    this.isRunning = false;
  }

  resetSimulation() {
    this.simulationService.reset();
    this.isRunning = false;
  }

  replaySimulation() {
    this.simulationService.replay();
    this.isRunning = true;
  }
}
