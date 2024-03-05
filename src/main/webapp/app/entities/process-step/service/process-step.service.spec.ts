import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProcessStep } from '../process-step.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../process-step.test-samples';

import { ProcessStepService } from './process-step.service';

const requireRestSample: IProcessStep = {
  ...sampleWithRequiredData,
};

describe('ProcessStep Service', () => {
  let service: ProcessStepService;
  let httpMock: HttpTestingController;
  let expectedResult: IProcessStep | IProcessStep[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProcessStepService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProcessStep', () => {
      const processStep = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(processStep).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProcessStep', () => {
      const processStep = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(processStep).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProcessStep', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProcessStep', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProcessStep', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProcessStepToCollectionIfMissing', () => {
      it('should add a ProcessStep to an empty array', () => {
        const processStep: IProcessStep = sampleWithRequiredData;
        expectedResult = service.addProcessStepToCollectionIfMissing([], processStep);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(processStep);
      });

      it('should not add a ProcessStep to an array that contains it', () => {
        const processStep: IProcessStep = sampleWithRequiredData;
        const processStepCollection: IProcessStep[] = [
          {
            ...processStep,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProcessStepToCollectionIfMissing(processStepCollection, processStep);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProcessStep to an array that doesn't contain it", () => {
        const processStep: IProcessStep = sampleWithRequiredData;
        const processStepCollection: IProcessStep[] = [sampleWithPartialData];
        expectedResult = service.addProcessStepToCollectionIfMissing(processStepCollection, processStep);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(processStep);
      });

      it('should add only unique ProcessStep to an array', () => {
        const processStepArray: IProcessStep[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const processStepCollection: IProcessStep[] = [sampleWithRequiredData];
        expectedResult = service.addProcessStepToCollectionIfMissing(processStepCollection, ...processStepArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const processStep: IProcessStep = sampleWithRequiredData;
        const processStep2: IProcessStep = sampleWithPartialData;
        expectedResult = service.addProcessStepToCollectionIfMissing([], processStep, processStep2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(processStep);
        expect(expectedResult).toContain(processStep2);
      });

      it('should accept null and undefined values', () => {
        const processStep: IProcessStep = sampleWithRequiredData;
        expectedResult = service.addProcessStepToCollectionIfMissing([], null, processStep, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(processStep);
      });

      it('should return initial array if no ProcessStep is added', () => {
        const processStepCollection: IProcessStep[] = [sampleWithRequiredData];
        expectedResult = service.addProcessStepToCollectionIfMissing(processStepCollection, undefined, null);
        expect(expectedResult).toEqual(processStepCollection);
      });
    });

    describe('compareProcessStep', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProcessStep(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProcessStep(entity1, entity2);
        const compareResult2 = service.compareProcessStep(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProcessStep(entity1, entity2);
        const compareResult2 = service.compareProcessStep(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProcessStep(entity1, entity2);
        const compareResult2 = service.compareProcessStep(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
