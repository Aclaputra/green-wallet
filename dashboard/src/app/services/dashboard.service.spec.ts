import { TestBed } from '@angular/core/testing';

<<<<<<< HEAD:dashboard/src/app/services/dashboard.service.spec.ts
import { DashboardService } from './dashboard.service';
=======
import { TransferService } from './transfer.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
>>>>>>> master:dashboard/src/app/services/transfer.service.spec.ts

describe('DashboardService', () => {
  let service: DashboardService;

  beforeEach(() => {
<<<<<<< HEAD:dashboard/src/app/services/dashboard.service.spec.ts
    TestBed.configureTestingModule({});
    service = TestBed.inject(DashboardService);
=======
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
    });
    service = TestBed.inject(TransferService);
>>>>>>> master:dashboard/src/app/services/transfer.service.spec.ts
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
