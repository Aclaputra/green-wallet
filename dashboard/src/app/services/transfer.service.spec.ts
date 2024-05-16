import { TestBed } from '@angular/core/testing';

import { TransferService } from './transfer.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TransferService', () => {
  let service: TransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
    });
    service = TestBed.inject(TransferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
