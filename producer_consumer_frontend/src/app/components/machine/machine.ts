import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MachineModel } from '../../models/machine/machine-module';
import { SimulationService } from '../../services/simulation-service';

@Component({
  selector: 'app-machine',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './machine.html',
  styleUrl: './machine.css',
})
export class MachineComponent implements OnChanges {

  @Input() machine!: MachineModel;

  isFlashing = false;

  constructor(private simulationService: SimulationService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['machine'] && this.machine?.status === 'IDLE') {
      this.flash();
    }
  }

  selectMachine() {
    this.simulationService.selectNode(
      this.machine.id,
      'MACHINE'
    );
  }

  private flash() {
    this.isFlashing = true;
    setTimeout(() => {
      this.isFlashing = false;
    }, 300);
  }
}
