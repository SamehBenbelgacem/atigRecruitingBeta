import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IObjectContainingFile } from '../object-containing-file.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../object-containing-file.test-samples';

import { ObjectContainingFileService } from './object-containing-file.service';

const requireRestSample: IObjectContainingFile = {
  ...sampleWithRequiredData,
};

describe('ObjectContainingFile Service', () => {
  let service: ObjectContainingFileService;
  let httpMock: HttpTestingController;
  let expectedResult: IObjectContainingFile | IObjectContainingFile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ObjectContainingFileService);
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

    it('should create a ObjectContainingFile', () => {
      const objectContainingFile = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(objectContainingFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ObjectContainingFile', () => {
      const objectContainingFile = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(objectContainingFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ObjectContainingFile', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ObjectContainingFile', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ObjectContainingFile', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addObjectContainingFileToCollectionIfMissing', () => {
      it('should add a ObjectContainingFile to an empty array', () => {
        const objectContainingFile: IObjectContainingFile = sampleWithRequiredData;
        expectedResult = service.addObjectContainingFileToCollectionIfMissing([], objectContainingFile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(objectContainingFile);
      });

      it('should not add a ObjectContainingFile to an array that contains it', () => {
        const objectContainingFile: IObjectContainingFile = sampleWithRequiredData;
        const objectContainingFileCollection: IObjectContainingFile[] = [
          {
            ...objectContainingFile,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addObjectContainingFileToCollectionIfMissing(objectContainingFileCollection, objectContainingFile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ObjectContainingFile to an array that doesn't contain it", () => {
        const objectContainingFile: IObjectContainingFile = sampleWithRequiredData;
        const objectContainingFileCollection: IObjectContainingFile[] = [sampleWithPartialData];
        expectedResult = service.addObjectContainingFileToCollectionIfMissing(objectContainingFileCollection, objectContainingFile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(objectContainingFile);
      });

      it('should add only unique ObjectContainingFile to an array', () => {
        const objectContainingFileArray: IObjectContainingFile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const objectContainingFileCollection: IObjectContainingFile[] = [sampleWithRequiredData];
        expectedResult = service.addObjectContainingFileToCollectionIfMissing(objectContainingFileCollection, ...objectContainingFileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const objectContainingFile: IObjectContainingFile = sampleWithRequiredData;
        const objectContainingFile2: IObjectContainingFile = sampleWithPartialData;
        expectedResult = service.addObjectContainingFileToCollectionIfMissing([], objectContainingFile, objectContainingFile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(objectContainingFile);
        expect(expectedResult).toContain(objectContainingFile2);
      });

      it('should accept null and undefined values', () => {
        const objectContainingFile: IObjectContainingFile = sampleWithRequiredData;
        expectedResult = service.addObjectContainingFileToCollectionIfMissing([], null, objectContainingFile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(objectContainingFile);
      });

      it('should return initial array if no ObjectContainingFile is added', () => {
        const objectContainingFileCollection: IObjectContainingFile[] = [sampleWithRequiredData];
        expectedResult = service.addObjectContainingFileToCollectionIfMissing(objectContainingFileCollection, undefined, null);
        expect(expectedResult).toEqual(objectContainingFileCollection);
      });
    });

    describe('compareObjectContainingFile', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareObjectContainingFile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareObjectContainingFile(entity1, entity2);
        const compareResult2 = service.compareObjectContainingFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareObjectContainingFile(entity1, entity2);
        const compareResult2 = service.compareObjectContainingFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareObjectContainingFile(entity1, entity2);
        const compareResult2 = service.compareObjectContainingFile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
