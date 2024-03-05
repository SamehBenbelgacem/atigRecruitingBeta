jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ProcessStepService } from '../service/process-step.service';

import { ProcessStepDeleteDialogComponent } from './process-step-delete-dialog.component';

describe('ProcessStep Management Delete Component', () => {
  let comp: ProcessStepDeleteDialogComponent;
  let fixture: ComponentFixture<ProcessStepDeleteDialogComponent>;
  let service: ProcessStepService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ProcessStepDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ProcessStepDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProcessStepDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProcessStepService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
