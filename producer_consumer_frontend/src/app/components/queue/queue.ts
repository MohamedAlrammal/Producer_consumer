import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueueModel } from '../../models/queue/queue-module';
import { SimulationStateModel } from '../../models/simulation-state/simulation-state-module';


@Component({
  selector: 'app-queue',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './queue.html',
  styleUrls: ['./queue.css']
})
export class QueueComponent {

  @Input() queue!: QueueModel;

  get queueSize(): number {
    return this.queue.products.length;
  }
}
