import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubEmail } from '../sub-email.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sub-email.test-samples';

import { SubEmailService, RestSubEmail } from './sub-email.service';

const requireRestSample: RestSubEmail = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
  snoozedTo: sampleWithRequiredData.snoozedTo?.toJSON(),
};

describe('SubEmail Service', () => {
  let service: SubEmailService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubEmail | ISubEmail[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubEmailService);
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

    it('should create a SubEmail', () => {
      const subEmail = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subEmail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubEmail', () => {
      const subEmail = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subEmail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubEmail', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubEmail', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SubEmail', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubEmailToCollectionIfMissing', () => {
      it('should add a SubEmail to an empty array', () => {
        const subEmail: ISubEmail = sampleWithRequiredData;
        expectedResult = service.addSubEmailToCollectionIfMissing([], subEmail);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subEmail);
      });

      it('should not add a SubEmail to an array that contains it', () => {
        const subEmail: ISubEmail = sampleWithRequiredData;
        const subEmailCollection: ISubEmail[] = [
          {
            ...subEmail,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubEmailToCollectionIfMissing(subEmailCollection, subEmail);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubEmail to an array that doesn't contain it", () => {
        const subEmail: ISubEmail = sampleWithRequiredData;
        const subEmailCollection: ISubEmail[] = [sampleWithPartialData];
        expectedResult = service.addSubEmailToCollectionIfMissing(subEmailCollection, subEmail);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subEmail);
      });

      it('should add only unique SubEmail to an array', () => {
        const subEmailArray: ISubEmail[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subEmailCollection: ISubEmail[] = [sampleWithRequiredData];
        expectedResult = service.addSubEmailToCollectionIfMissing(subEmailCollection, ...subEmailArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subEmail: ISubEmail = sampleWithRequiredData;
        const subEmail2: ISubEmail = sampleWithPartialData;
        expectedResult = service.addSubEmailToCollectionIfMissing([], subEmail, subEmail2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subEmail);
        expect(expectedResult).toContain(subEmail2);
      });

      it('should accept null and undefined values', () => {
        const subEmail: ISubEmail = sampleWithRequiredData;
        expectedResult = service.addSubEmailToCollectionIfMissing([], null, subEmail, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subEmail);
      });

      it('should return initial array if no SubEmail is added', () => {
        const subEmailCollection: ISubEmail[] = [sampleWithRequiredData];
        expectedResult = service.addSubEmailToCollectionIfMissing(subEmailCollection, undefined, null);
        expect(expectedResult).toEqual(subEmailCollection);
      });
    });

    describe('compareSubEmail', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubEmail(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubEmail(entity1, entity2);
        const compareResult2 = service.compareSubEmail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubEmail(entity1, entity2);
        const compareResult2 = service.compareSubEmail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubEmail(entity1, entity2);
        const compareResult2 = service.compareSubEmail(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
