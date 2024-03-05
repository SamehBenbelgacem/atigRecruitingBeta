import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../candidate-additional-infos.test-samples';

import { CandidateAdditionalInfosService, RestCandidateAdditionalInfos } from './candidate-additional-infos.service';

const requireRestSample: RestCandidateAdditionalInfos = {
  ...sampleWithRequiredData,
  birthday: sampleWithRequiredData.birthday?.toJSON(),
  firstContact: sampleWithRequiredData.firstContact?.toJSON(),
};

describe('CandidateAdditionalInfos Service', () => {
  let service: CandidateAdditionalInfosService;
  let httpMock: HttpTestingController;
  let expectedResult: ICandidateAdditionalInfos | ICandidateAdditionalInfos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CandidateAdditionalInfosService);
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

    it('should create a CandidateAdditionalInfos', () => {
      const candidateAdditionalInfos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(candidateAdditionalInfos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CandidateAdditionalInfos', () => {
      const candidateAdditionalInfos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(candidateAdditionalInfos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CandidateAdditionalInfos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CandidateAdditionalInfos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CandidateAdditionalInfos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCandidateAdditionalInfosToCollectionIfMissing', () => {
      it('should add a CandidateAdditionalInfos to an empty array', () => {
        const candidateAdditionalInfos: ICandidateAdditionalInfos = sampleWithRequiredData;
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing([], candidateAdditionalInfos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(candidateAdditionalInfos);
      });

      it('should not add a CandidateAdditionalInfos to an array that contains it', () => {
        const candidateAdditionalInfos: ICandidateAdditionalInfos = sampleWithRequiredData;
        const candidateAdditionalInfosCollection: ICandidateAdditionalInfos[] = [
          {
            ...candidateAdditionalInfos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing(
          candidateAdditionalInfosCollection,
          candidateAdditionalInfos,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CandidateAdditionalInfos to an array that doesn't contain it", () => {
        const candidateAdditionalInfos: ICandidateAdditionalInfos = sampleWithRequiredData;
        const candidateAdditionalInfosCollection: ICandidateAdditionalInfos[] = [sampleWithPartialData];
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing(
          candidateAdditionalInfosCollection,
          candidateAdditionalInfos,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(candidateAdditionalInfos);
      });

      it('should add only unique CandidateAdditionalInfos to an array', () => {
        const candidateAdditionalInfosArray: ICandidateAdditionalInfos[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const candidateAdditionalInfosCollection: ICandidateAdditionalInfos[] = [sampleWithRequiredData];
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing(
          candidateAdditionalInfosCollection,
          ...candidateAdditionalInfosArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const candidateAdditionalInfos: ICandidateAdditionalInfos = sampleWithRequiredData;
        const candidateAdditionalInfos2: ICandidateAdditionalInfos = sampleWithPartialData;
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing([], candidateAdditionalInfos, candidateAdditionalInfos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(candidateAdditionalInfos);
        expect(expectedResult).toContain(candidateAdditionalInfos2);
      });

      it('should accept null and undefined values', () => {
        const candidateAdditionalInfos: ICandidateAdditionalInfos = sampleWithRequiredData;
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing([], null, candidateAdditionalInfos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(candidateAdditionalInfos);
      });

      it('should return initial array if no CandidateAdditionalInfos is added', () => {
        const candidateAdditionalInfosCollection: ICandidateAdditionalInfos[] = [sampleWithRequiredData];
        expectedResult = service.addCandidateAdditionalInfosToCollectionIfMissing(candidateAdditionalInfosCollection, undefined, null);
        expect(expectedResult).toEqual(candidateAdditionalInfosCollection);
      });
    });

    describe('compareCandidateAdditionalInfos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCandidateAdditionalInfos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCandidateAdditionalInfos(entity1, entity2);
        const compareResult2 = service.compareCandidateAdditionalInfos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCandidateAdditionalInfos(entity1, entity2);
        const compareResult2 = service.compareCandidateAdditionalInfos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCandidateAdditionalInfos(entity1, entity2);
        const compareResult2 = service.compareCandidateAdditionalInfos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
