import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDesider } from '../desider.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../desider.test-samples';

import { DesiderService } from './desider.service';

const requireRestSample: IDesider = {
  ...sampleWithRequiredData,
};

describe('Desider Service', () => {
  let service: DesiderService;
  let httpMock: HttpTestingController;
  let expectedResult: IDesider | IDesider[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DesiderService);
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

    it('should create a Desider', () => {
      const desider = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(desider).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Desider', () => {
      const desider = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(desider).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Desider', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Desider', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Desider', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDesiderToCollectionIfMissing', () => {
      it('should add a Desider to an empty array', () => {
        const desider: IDesider = sampleWithRequiredData;
        expectedResult = service.addDesiderToCollectionIfMissing([], desider);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(desider);
      });

      it('should not add a Desider to an array that contains it', () => {
        const desider: IDesider = sampleWithRequiredData;
        const desiderCollection: IDesider[] = [
          {
            ...desider,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDesiderToCollectionIfMissing(desiderCollection, desider);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Desider to an array that doesn't contain it", () => {
        const desider: IDesider = sampleWithRequiredData;
        const desiderCollection: IDesider[] = [sampleWithPartialData];
        expectedResult = service.addDesiderToCollectionIfMissing(desiderCollection, desider);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(desider);
      });

      it('should add only unique Desider to an array', () => {
        const desiderArray: IDesider[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const desiderCollection: IDesider[] = [sampleWithRequiredData];
        expectedResult = service.addDesiderToCollectionIfMissing(desiderCollection, ...desiderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const desider: IDesider = sampleWithRequiredData;
        const desider2: IDesider = sampleWithPartialData;
        expectedResult = service.addDesiderToCollectionIfMissing([], desider, desider2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(desider);
        expect(expectedResult).toContain(desider2);
      });

      it('should accept null and undefined values', () => {
        const desider: IDesider = sampleWithRequiredData;
        expectedResult = service.addDesiderToCollectionIfMissing([], null, desider, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(desider);
      });

      it('should return initial array if no Desider is added', () => {
        const desiderCollection: IDesider[] = [sampleWithRequiredData];
        expectedResult = service.addDesiderToCollectionIfMissing(desiderCollection, undefined, null);
        expect(expectedResult).toEqual(desiderCollection);
      });
    });

    describe('compareDesider', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDesider(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDesider(entity1, entity2);
        const compareResult2 = service.compareDesider(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDesider(entity1, entity2);
        const compareResult2 = service.compareDesider(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDesider(entity1, entity2);
        const compareResult2 = service.compareDesider(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
